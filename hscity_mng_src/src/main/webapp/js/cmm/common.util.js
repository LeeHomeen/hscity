var CM = (function(){
    $(document).ready(function(){
        // input 엔터시 버튼 클릭이벤트 주기
        $('input[type="text"]').on('keydown', function(e){
            if(e.keyCode === 13) {
                var id = $(this).data('for');
                $('#' + id).trigger('click');
            }
        });

        // 한글입력막기 스크립트
        $(".no-kor").keyup(function(e) {
            if(e.keyCode === 8 || e.keyCode === 9 || e.keyCode === 37 || e.keyCode === 39 || e.keyCode === 46 ) {
                return;
            }
            var v = $(this).val();
            $(this).val(v.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, ''));
        });
    });

    String.prototype.string = function (len) {
        var s = '', i = 0;
        while (i++ < len) {
            s += this;
        }
        return s;
    };

    String.prototype.zf = function (len) {
        return "0".string(len - this.length) + this;
    };

    Number.prototype.zf = function (len) {
        return this.toString().zf(len);
    };

    String.prototype.format = function () {
        var a = this;
        for (var k in arguments) {
            a = a.replace("{" + k + "}", arguments[k])
        }
        return a
    };

    // 폼태그 안에 요소(input, select, checkbox, radio) 를 json 오브젝트로 변환
    $.fn.serializeObject = function () {
        var o = {};
        var nm = "";
        var val = "";
        $(this).find("input[type='hidden'], input[type='text'], input[type='password'], input[type='checkbox']:checked, input[type='radio']:checked, select,textarea").each(function () {
            nm = $(this).attr('name') || $(this).attr('id')
            // mask 처리 된 경우, cleanVal 함수를 통해 마스크 제거된 값을 가져오게 처리
            try {
                val = $(this).cleanVal();
            } catch (E) {
                val = $(this).val();
            }

            if (o[nm] !== undefined) {
                if (!o[nm].push) {
                    o[nm] = [o[nm]];
                }

                o[nm].push(val || '');
            } else {
                o[nm] = val || '';
            }
        });
        return o;
    };

    // 폼초기화
    $.fn.formReset = function() {
        this.each(function() {
            this.reset();
        });
    };

    return {
        contextPath: null,

        interval: null,

        /**
         * 스마트 에디터 초기화
         *
         * @param id textarea id
         * @returns {Array}
         */
        initSmartEditor: function(id) {
            var oEditors = [];
            nhn.husky.EZCreator.createInIFrame({
                oAppRef: oEditors,
                elPlaceHolder: id,
                sSkinURI: '/js/lib/smarteditor/SmartEditor2Skin.html',
                htParams : {
                    bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                    bUseVerticalResizer : false,
                    bUseModeChanger : false,
                    fOnBeforeUnload : function(){
                        //alert("완료!");
                    },
                    I18N_LOCALE : 'ko_KR' //default = ko_KR
                },
                fOnAppLoad : function(){
                    //oEditors.getById["contents"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
                },
                fCreator: "createSEditor2"
            });

            return oEditors;
        },

        postFileDownload: function(url, json) {
            var $iframe = $('#downloadIfrm');
            if($iframe.length < 1) {
                $iframe = $('<iframe>', {
                    id: 'downloadIfrm',
                    name: 'downloadIfrm'
                }).css({
                    'display': 'none'
                });
            } else {
                $iframe = $('#')
            }

            var $form = $('#downloadForm');
            if($form.length < 1) {
                $form = $('<form>', {
                    id: 'downloadForm',
                    method: 'post',
                    action: url,
                    target: 'downloadIfrm'
                }).css({
                    'display': 'none'
                });
            } else {
                $form.attr('action', url);
                $form.find('input').remove();
            }

            var $input;
            for(var key in json) {
                $input = $('<input>', {
                    name: key,
                    value: json[key]
                });

                $form.append($input);
            }

            $('body').append($iframe);
            $('body').append($form);

            $iframe.load(function(){
                $form.remove();
                $iframe.remove();
            });

            $form.submit();
        },

        /**
         * 달력을 생성한다.
         * @param  {String} id   달력을 생성할 <input /> 의 ID
         * @param  {Number} size <input /> 의 size
         */
        createDatepicker : function(id, size) {
            $( "#"+id ).width(size*8).datepicker({
                changeMonth: true,
                changeYear: true,
                numberOfMonths: 1,
                dateFormat: 'yy-mm-dd',
                dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
                monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
            });
        },

        /**
         * 달력을 생성한다. 시작일과 종료일 범위를 지정할때 사용한다.
         * @param  {String} start_id [description]
         * @param  {String} end_id   [description]
         * @param  {[type]} size     [description]
         * @return {[type]}          [description]
         */
        createDatepickerLinked : function(start_id, end_id, size) {
            $( "#"+start_id ).width(size*8).datepicker({
                changeMonth: true,
                changeYear: true,
                numberOfMonths: 1,
                dateFormat: 'yy-mm-dd',
                dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
                monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                yearRange: 'c-100:c+100'
                ,onClose: function( selectedDate ) {
                    $( "#"+end_id ).datepicker( "option", "minDate", selectedDate );
                }
            });
            $( "#"+end_id ).width(size*8).datepicker({
                changeMonth: true,
                changeYear: true,
                numberOfMonths: 1,
                dateFormat: 'yy-mm-dd',
                dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
                monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                yearRange: 'c-100:c+100'
                ,onClose: function( selectedDate ) {
                    $( "#"+start_id ).datepicker( "option", "maxDate", selectedDate );
                }
            });
        },

        /**
         * 월 선택 달력을 생성한다.
         *
         * @param id
         * @param size
         */
        createMonthpicker: function(id, size) {
            $("#" + id).width(size * 8).monthpicker({
                changeYear: true
                , pattern: 'yyyy-mm'
                , yearSuffix: ''
                , prevText: '이전'
                , nextText: '다음'
                , monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
            });
        },

        /**
         * 파일 다운로드시 Iframe을 사용한다.
         *
         * @param $dom
         * @param url
         * @param datas
         */
        downloadFileWithIFrame: function($dom, url, datas) {
            var $iframe = $('#downIfrm');
            if($iframe.length < 1) {
                $iframe = $('<iframe>', {
                    id: 'downIfrm',
                    name: 'downIfrm'
                }).css({
                    'display': 'none'
                });
            }

            var $form = $('#downForm');
            if($form.length < 1) {
                $form = $('<form>', {
                    id: 'downForm',
                    method: 'post',
                    action: url,
                    target: 'downIfrm'
                }).css({
                    'display': 'none'
                });
            } else {
                $form.attr('action', url);
                $form.find('input').remove();
            }

            for(var key in datas) {
                var $input = $('<input>', {
                    value: datas[key],
                    name: key
                });
                $form.append($input);
            }

            $dom.append($iframe);
            $dom.append($form);
            $form.submit();

            // ajax 요청을 통한 파일 다운로드 체크
            CM.showMask();
            CM.interval = setInterval(function(){
                $.ajax({
                    url: '/com/downloadCheck.do',
                    datatType: 'json',
                    cache: false,
                    success: function(result) {
                        if(!result) {
                            CM.hideMask();
                            clearInterval(CM.interval);
                        }
                    }
                });
            }, 500);
        },

        stringToXMLDom: function (string) {
            var xmlDoc = null;
            if (window.DOMParser) {
                var parser = new DOMParser();
                xmlDoc = parser.parseFromString(string, "text/xml");
            } else { // Internet Explorer
                xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
                xmlDoc.async = "false";
                xmlDoc.loadXML(string);
            }
            return xmlDoc;
        },

        /**
         * 마스크 생성
         */
        showMask: function() {
            var $maskContainer = $('<div/>',{
                id: '_maskContainer'
            }).css({
                'position': 'absolute',
                'zIndex' : 9999,
                'width': '100%',
                'height': '100%',
                'top' : '0',
                'left': '0',
                'backgroundColor': 'rgba(16, 16, 16, 0.5)'
            });

            $('body').append($maskContainer);

            var $mask = $('<div/>', {
                id: '_mask'
            }).css({
                'position': 'absolute',
                'top' : $maskContainer.height() / 2 - 83.5,
                'left': $maskContainer.width() / 2 - 128,
                'width': '128px',
                'height': '75px'
            });

            var $img = $('<img/>', {
                src: '/images/cmm/img-loading2.gif'
            });

            $mask.append($img);
            $maskContainer.append($mask);
        },

        /**
         * 마스크 제거
         */
        hideMask: function() {
            $('#_maskContainer').remove();
        }
    }
}());