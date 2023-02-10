/**
 * @requires OpenLayers/Control.js
 */

/**
 * Class: OpenLayers.Control.DaumRoadViewClick
 * 반경 검색을 위한 컨트롤입니다. 컨트롤을 활성화 한후 맵을 클릭하면
 * 클릭한 위치에 원을 그리며 그려진 원의 반경에 포함된 Feature를 검색합니다.
 * 검색대상 레이어는 필수 옵션입니다.
 *
 * Inherits from:
 *  - <OpenLayers.Control>
 */
OpenLayers.Control.DaumRoadViewClick = OpenLayers.Class(OpenLayers.Control, {

    EVENT_TYPES: ['callback'],

    /**
     * Property: layer
     * {<OpenLayers.Layer.Vector>} 반경검색시 원이 그려질 벡터레이어
     */
    layer: null,

    /**
     * Property: handler
     * {<OpenLayers.Handler>} DaumRoadViewClick 컨트롤에서 사용할 핸들러
     */
    handler: OpenLayers.Handler.Point,


    /**
     * Constructor: OpenLayers.Control.DaumRoadViewClick
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
        
        var defaultStyles = OpenLayers.Control.DaumRoadViewClick.styles;
        
        var style = new OpenLayers.Style(null, {rules: [
            new OpenLayers.Rule({symbolizer: options ? options.symbolizer : false || {
                'Point': defaultStyles.Point
            }})
        ]});
        
        this.layer.styleMap = new OpenLayers.StyleMap({ 'default' : style });
        
        var handlerOptions = options ? options.handlerOptions : false || {};
        
        if(!handlerOptions.layerOptions) {
            handlerOptions.layerOptions = { styleMap: this.layer.styleMap };
        }
        
        this.handler = new this.handler(this, { done: this.trigger }, handlerOptions);
    },

    trigger: function(e) {

        var control = this;
        control.events.triggerEvent(control.EVENT_TYPES[0], e);

    },

    CLASS_NAME: 'OpenLayers.Control.DaumRoadViewClick'
});

/**
 * Constant: OpenLayers.Control.DaumRoadViewClick
 * Contains the keys: "Point", "Line", "Polygon" as a objects with style keys.
 */
OpenLayers.Control.DaumRoadViewClick.styles = {
    'Point': {
        externalGraphic: 'http://i1.daumcdn.net/localimg/localimages/07/mapapidoc/roadview_wk.png',
        graphicOpacity: 1,
        graphicWidth: 35,
        graphicHeight: 39,
        graphicXOffset: -17,
        graphicYOffset: -39
    }
};