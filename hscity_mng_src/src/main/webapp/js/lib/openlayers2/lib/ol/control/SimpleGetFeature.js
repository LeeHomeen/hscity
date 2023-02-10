/**
 * @requires OpenLayers/Control.js
 */

/**
 * Class: OpenLayers.Control.SimpleSearch
 * 반경 검색을 위한 컨트롤입니다. 컨트롤을 활성화 한후 맵을 클릭하면
 * 클릭한 위치에 원을 그리며 그려진 원의 반경에 포함된 Feature를 검색합니다.
 * 검색대상 레이어는 필수 옵션입니다.
 *
 * Inherits from:
 *  - <OpenLayers.Control>
 */
OpenLayers.Control.SimpleGetFeature = OpenLayers.Class(OpenLayers.Control, {
    /**
     * Property: EVENT_TYPES
     * {Array} {String} 이벤트 타입 (요청 완료 시 실행할 이벤트)
     */
    EVENT_TYPES: ['beforeCallback', 'callback'],

    /**
     * Property: layer
     * {<OpenLayers.Layer.Vector>} 반경검색시 원이 그려질 벡터레이어
     */
    layer: null,

    /**
     * Property: handler
     * {<OpenLayers.Handler>} SimpleGetFeature 컨트롤에서 사용할 핸들러
     */
    handler: OpenLayers.Handler.Point,

    protocolConfig: null,

    /**
     * Property: persist
     * {Boolean} 반경 검색 원 유지 옵션
     */
    persist: false,

    /**
     * Property: isDynamicDistance
     * {Boolean} 줌레벨에 따라 distance 변동 여부
     * default: true
     */
    isDynamicDistance: true,

    projection: 'EPSG:5179',

    workSpace: 'bigcy',

    /**
     * Property: maxFeatures
     * {Number} 최대 Feature 검색 수
     */
    maxFeatures : 9999,

    /**
     * Constructor: OpenLayers.Control.SimpleGetFeature
     * 반경검색 컨트롤 생성자
     *
     * Parameters:
     * requireOptions - {Object}
     *      layer - {<OpenLayers.Layer.Vector>} 반경을 그릴 벡터레이어
     *      protocol - {<OpenLayers.Protocol.WFS>}
     * options - {<OpenLayers.Control>} The control that initialized this
     *     handler.  The control is assumed to have a valid map property; that
     *     map is used in the handler's own setMap method.  If a map property
     *     is present in the options argument it will be used instead.
     */
    initialize: function(requireOptions, options) {

        OpenLayers.Util.extend(this, requireOptions);

        OpenLayers.Control.prototype.initialize.apply(this, [options]);

        var defaultStyles = OpenLayers.Control.SimpleGetFeature.styles;

        var style = new OpenLayers.Style(null, {rules: [
            new OpenLayers.Rule({symbolizer: {
                'Point': defaultStyles.Point,
                'Line': defaultStyles.Line,
                'Polygon': defaultStyles.Polygon
            }})
        ]});

        this.projection = this.projection;
        this.workSpace = this.workSpace;

        this.layer.styleMap = new OpenLayers.StyleMap({ 'default' : style });

        var handlerOptions = {};

        if(!handlerOptions.layerOptions) {
            handlerOptions.layerOptions = { styleMap: this.layer.styleMap };
        }

        handlerOptions.mousedown = function(evt) {

            return OpenLayers.Event.isLeftClick(evt) && this.down(evt);
        };

        this.handler = new this.handler(this, { done: this.getFeature }, handlerOptions);
    },

    /**
     * APIMethod: getFeature
     * 지도 클릭시 반경을 그리며 그려진 반경으로 WITHIN WFS GetFeature 요청
     *
     * Parameters:
     * geometry - {<OpenLayers.Geometry.Point>} 클릭한 지점의 Point 좌표
     */
    getFeature: function(geometry) {
        if(!this.persist) this.layer.removeAllFeatures();



        var control = this;

        var beforeParam = {
            featureTypes: []
        };
        control.events.triggerEvent(control.EVENT_TYPES[0], beforeParam);

        var protocol = new OpenLayers.Protocol.WFS({
            version: this.protocolConfig.version,
            url: this.protocolConfig.url,
            featureType: beforeParam.featureTypes,
            // featureType: this.protocolConfig.featureType,
            // featureNS: this.protocolConfig.featureNS,
            // featurePrefix: this.protocolConfig.featurePrefix,
            srsName: this.map.getProjection()
        });

        var filter = new OpenLayers.Filter.Spatial({
            type: OpenLayers.Filter.Spatial.DWITHIN,
            // property : protocol.geometryType,
            distance: this.getDistance(),
            distanceUnits: 'm',
            value: geometry
        });

        protocol.read({
            filter : filter,
            maxFeatures : this.maxFeatures,
            callback : function(e) {
                e.geometry = geometry;

                control.events.triggerEvent(control.EVENT_TYPES[1], e);
            }
        });
    },

    getDistance: function() {

        var lonlat = this.map.getCenter();

        var center_px = this.map.getViewPortPxFromLonLat(lonlat);

        var center_px_clone = center_px.clone();
        center_px_clone.x = center_px_clone.x + 10;

        var clone_lonlat = this.map.getLonLatFromPixel(center_px_clone);

        var center_point = new OpenLayers.Geometry.Point(lonlat.lon, lonlat.lat);

        var center_point_10 = new OpenLayers.Geometry.Point(clone_lonlat.lon, clone_lonlat.lat);


        return center_point.distanceTo(center_point_10);

        // var distance = 1;
        //
        // if(this.isDynamicDistance) {
        //     if(this.map.getProjection() === 'EPSG:5179') {
        //         distance = Math.pow(2, (13 - this.map.getZoom())+3);
        //     } else if(this.map.getProjection() === 'EPSG:900913') {
        //         distance = Math.pow(2, (19 - this.map.getZoom())+3);
        //     }
        // }
        // return distance;

    },

    CLASS_NAME: 'OpenLayers.Control.SimpleGetFeature'
});

/**
 * Constant: OpenLayers.Control.SimpleGetFeature
 * Contains the keys: "Point", "Line", "Polygon" as a objects with style keys.
 */
OpenLayers.Control.SimpleGetFeature.styles = {
    'Point': {
        pointRadius: 4,
        graphicName: 'square',
        fillColor: 'white',
        fillOpacity: 1,
        strokeWidth: 1,
        strokeOpacity: 1,
        strokeColor: '#333333'
    },
    'Line': {
        strokeWidth: 2,
        strokeOpacity: 0,
        strokeColor: '#666666',
        strokeDashstyle: 'dash'
    },
    'Polygon': {
        strokeWidth: 2,
        strokeOpacity: 0,
        strokeColor: '#666666',
        strokeDashstyle: 'dash',
        fillColor: 'white',
        fillOpacity: 0,
        fontSize: '11px',
        fontColor: '#800517',
        fontFamily: 'Verdana',
        labelOutlineColor: '#dddddd',
        labelAlign: 'cm',
        labelOutlineWidth: 2
    }
};
