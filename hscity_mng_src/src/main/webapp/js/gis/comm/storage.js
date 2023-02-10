/***
 * ja 저장 변수 등록
 */
var popMap = null; //인구&매출분석 지도
var trafficMap = null; //교통분석 지도

//다음 키워드 분석
var daumKeyword = null;

//멀티셀렉트 최상위 객체
var topParent = null;

//시군구명칭 제거 
var removeSiInfo = ["수원시","성남시","안양시","안산시","고양시","용인시","청주시","천안시","전주시","포항시","창원시"];

var legendUrl = new Map();
legendUrl.put("1-1-1", "/gis/pop/popYearLegend.do");
legendUrl.put("1-1-2", "/gis/pop/popGALegend.do");
legendUrl.put("1-2-1", "/gis/fpop/fPopYearLegend.do");
legendUrl.put("1-4-1", "/gis/sale/saleYearLegend.do");
legendUrl.put("1-4-2", "/gis/sale/saleGALegend.do");
legendUrl.put("1-4-3", "/gis/sale/saleTimeLegend.do");
legendUrl.put("1-4-4", "/gis/sale/saleCtgLegend.do");


//범례 색상정보
var legendRed = new Map();
legendRed.put("0", "#FFFFFF");
legendRed.put("1", "#FFC6C6");
legendRed.put("2", "#FFA2A2");
legendRed.put("3", "#FF7E7E");
legendRed.put("4", "#FF5A5A");
legendRed.put("5", "#FF3636");
legendRed.put("6", "#FF1212");
legendRed.put("7", "#ED0000");
legendRed.put("8", "#C90000");
legendRed.put("9", "#A50000");
legendRed.put("10", "#810000");
legendRed.put("11", "#000000");

var legendGreen = new Map();
legendGreen.put("0", "#FFFFFF");
legendGreen.put("1", "#E8FFE2");
legendGreen.put("2", "#C4FFBE");
legendGreen.put("3", "#A0F29A");
legendGreen.put("4", "#7CCE76");
legendGreen.put("5", "#58AA52");
legendGreen.put("6", "#34862E");
legendGreen.put("7", "#10620A");
legendGreen.put("8", "#003E00");
legendGreen.put("9", "#001A00");
legendGreen.put("10", "#000800");
legendGreen.put("11", "#000000");

var legendBlue = new Map();
legendBlue.put("0", "#FFFFFF");
legendBlue.put("1", "#C6F9FF");
legendBlue.put("2", "#A2D5FF");
legendBlue.put("3", "#7EB1FF");
legendBlue.put("4", "#5A8DF3");
legendBlue.put("5", "#3669CF");
legendBlue.put("6", "#1245AB");
legendBlue.put("7", "#002187");
legendBlue.put("8", "#000063");
legendBlue.put("9", "#000051");
legendBlue.put("10", "#00003F");
legendBlue.put("11", "#000000");


//////////////////////////////////////////////////////////////////////////////////
//////////////////////// RESULT GRID COLS SETTING ////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
//결과 그리드 cols정의
var gridCols = [];
var gridWidthMap = new Map(); //그리드 최소 사이즈 저장

/**
 * 인구매출 분석(1) : 인구분석(1)_기간별 분석(1)
 */
var cols111 = [
    //{ label: '번호'  , name: 'id',    width: 40,  align:'center',  key:true, hidden:true },
    { label: '구분'  , name: 'div',  width: 60,  align:'center'},
    { label: 'item'  , name: 'item',  width: 0,  align:'center', hidden:true},
    { label: '기간'  , name: 'cst',  width: 60,  align:'center',  formatter:cstItemDate, sorttype:'number'},
    { label: '10세미만(명)'  , name: 'a',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '10대(명)'  , name: 'b',  width: 60,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '20대(명)'  , name: 'c',  width: 60,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '30대(명)'  , name: 'd',  width: 60,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '40대(명)'  , name: 'e',  width: 60,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '50대(명)'  , name: 'f',  width: 60,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '60대(명)'  , name: 'g',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '70대(명)'  , name: 'h',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '80대(명)'  , name: 'i',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '90대(명)'  , name: 'j',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '100세이상(명)'  , name: 'k',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
    { label: '합계(명)'  , name: 'tot',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort }
];
gridWidthMap.put("cols111" , 930); //width최소값

/**
 * 인구매출 분석(1) : 인구분석(1)_성/연령별 분석(2)
 */
var cols112 = [
   { label: '구분'  , name: 'div',    width: 60,  align:'center'},
   { label: 'ym'  , name: 'ym',  width: 0,  align:'center', hidden:true },
   { label: 'age'  , name: 'item',  width: 0,  align:'center', hidden:true },
   { label: '연령'  , name: 'age',  width: 55,  align:'center', sorttype:datePopSort },
   { label: '남자인구(명)'  , name: 'mcnt',   width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '여자인구(명)'  , name: 'wcnt',   width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },  
   { label: '합계(명)'  , name: 'tot',   width: 85,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort  }
];
gridWidthMap.put("cols112" , 360); //width최소값

/**
 * 인구분석_전입분석
 */
var cols1131 = [
   { label: '전입지역'  , name: 'item', width:140,  align:'center'},
   { label: '전입자수(명)'  , name: 'cnt',  width:85,  align:'center',  formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },  
];
gridWidthMap.put("cols1131" , 225); //width최소값
var cols1132 = [
    { label: '전입요인' , name: 'item', width:80,  align:'center'},
    { label: '건수(건)'    , name: 'cnt',  width:85,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },  
 ];
gridWidthMap.put("cols1132" , 165); //width최소값

/**
 * 인구분석_전출분석
 */
var cols1141 = [
   { label: '전출지역'  , name: 'item', width:140,  align:'center'},
   { label: '전출자수(명)'  , name: 'cnt',  width:85,  align:'center',  formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },  
];
gridWidthMap.put("cols1141" , 225); //width최소값
var cols1142 = [
    { label: '전출요인' , name: 'item', width:80,  align:'center'},
    { label: '건수(건)'    , name: 'cnt',  width:85,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort},  
 ];
gridWidthMap.put("cols1142" , 165); //width최소값


/**
 * 유동인구_기간별 분석
 */
var cols121 = [              
   { label: '구분'   , name: 'div',  width: 60,  align:'center'},
   { label: 'item'   , name: 'item', width: 0,  align:'center', hidden:true },   
   { label: 'emdcd'   , name: 'emdcd', width: 0,  align:'center', hidden:true },
   { label: '기간'  , name: 'cst',  width: 70,  align:'center', formatter:cstItemDate, sorttype:'number'},
   { label: '10대(명)'  , name: 'p10',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '20대(명)'  , name: 'p20',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '30대(명)'  , name: 'p30',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '40대(명)'  , name: 'p40',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '50대(명)'  , name: 'p50',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '60대(명)'  , name: 'p60',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '70대이상(명)', name: 'p70',  width: 85,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },   
   { label: '합계(명)'   , name: 'tot',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort }
];
gridWidthMap.put("cols121", 705); //width최소값

/**
 * 유동인구_성연령별 분석
 */
var cols122 = [
   { label: '구분'       , name: 'div',  width: 60,  align:'center' },
   { label: '10대_남(명)'   , name: 'm10',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '20대_남(명)'   , name: 'm20',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '30대_남(명)'   , name: 'm30',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '40대_남(명)'   , name: 'm40',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '50대_남(명)'   , name: 'm50',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '60대_남(명)'   , name: 'm60',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '70대이상_남(명)', name: 'm70',  width: 90,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '10대_여(명)'   , name: 'w10',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '20대_여(명)'   , name: 'w20',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '30대_여(명)'   , name: 'w30',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '40대_여(명)'   , name: 'w40',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '50대_여(명)'   , name: 'w50',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '60대_여(명)'   , name: 'w60',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '70대이상_여(명)', name: 'w70',  width: 90,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '합계(명)'       , name: 'tot',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort }
];
gridWidthMap.put("cols122", 1280); //width최소값


/**
 * 유동인구_시간대별 분석
 */
var cols123 = [
   { label: '구분'  , name: 'div',  width: 60,  align:'center' },
   { label: '00시(명)' , name: 't00',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort  },
   { label: '01시(명)' , name: 't01',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '02시(명)' , name: 't02',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '03시(명)' , name: 't03',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '04시(명)' , name: 't04',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '05시(명)' , name: 't05',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '06시(명)' , name: 't06',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '07시(명)' , name: 't07',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '08시(명)' , name: 't08',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '09시(명)' , name: 't09',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '10시(명)' , name: 't10',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '11시(명)' , name: 't11',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '12시(명)' , name: 't12',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '13시(명)' , name: 't13',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '14시(명)' , name: 't14',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '15시(명)' , name: 't15',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '16시(명)' , name: 't16',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '17시(명)' , name: 't17',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '18시(명)' , name: 't18',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '19시(명)' , name: 't19',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '20시(명)' , name: 't20',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '21시(명)' , name: 't21',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '22시(명)' , name: 't22',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '23시(명)' , name: 't23',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '합계(명)'  , name: 'tot',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort }
];
gridWidthMap.put("cols123", 2060); //width최소값


/**
 * 유입인구_시도별
 */
var cols131 = [
   { label: '유입시도'  , name: 'div',   width:100,   align:'center' },     
   { label: '유입인구(명)'  , name: 'tot',   width: 130, align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort }
];
gridWidthMap.put("cols131", 230); //width최소값

/**
 * 유입인구_시군구별
 */
var cols132 = [
   { label: '유입시군'  , name: 'div',   width:100,   align:'center' },     
   { label: '유입인구(명)'  , name: 'tot',   width: 130, align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort}
];
gridWidthMap.put("cols132", 230); //width최소값

/**
 * 유입인구_성연령별
 */
var cols133 = [
   { label: '유입시군'       , name: 'div',  width: 100,  align:'center' },
   { label: '10대_남(명)'   , name: 'm10',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '20대_남(명)'   , name: 'm20',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '30대_남(명)'   , name: 'm30',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '40대_남(명)'   , name: 'm40',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '50대_남(명)'   , name: 'm50',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '60대_남(명)'   , name: 'm60',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '70대이상_남(명)', name: 'm70',  width: 90,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '10대_여(명)'   , name: 'w10',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '20대_여(명)'   , name: 'w20',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '30대_여(명)'   , name: 'w30',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '40대_여(명)'   , name: 'w40',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '50대_여(명)'   , name: 'w50',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '60대_여(명)'   , name: 'w60',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '70대이상_여(명)', name: 'w70',  width: 90,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '합계'       , name: 'tot',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort }
];
gridWidthMap.put("cols133", 1320); //width최소값

/**
 * 매출분석_기간별
 */
var cols141 = [
    { label: '구분'  , name: 'div', width:70,  align:'center'},
    { label: 'item'   , name: 'item',  width: 0,  align:'center', hidden:true},
    { label: '기간'  , name: 'cst',  width: 70,  align:'center',  formatter:cstItemDate, sorttype:datePopSort},
    { label: '내지인(원)'  , name: 'apop',   width: 100,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort},
    { label: '외지인(원)'  , name: 'bpop',   width: 100,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort},
    { label: '인접지인(원)' , name: 'cpop',   width: 100,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort},
    { label: '합계(원)'  , name: 'tot',   width: 110,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort}
 ];
gridWidthMap.put("cols141", 550); //width최소값

/**
 * 매출분석_성연령별
 */
var cols142 = [
    { label: '구분'    ,name: 'div', width:70,  align:'center'},    
    { label: 'item'  ,name: 'item',   width: 0,  align:'center', hidden:true},
    { label: '연령'    ,name: 'gb',  width:70,  align:'center', sorttype:datePopSort},
    { label: '남자매출(원)' ,name: 'mcnt',   width: 100,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort},
    { label: '여자매출(원)' ,name: 'wcnt',   width: 100,  align:'center', formatter:'currency',  formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort},    
    { label: '합계(원)'    ,name: 'tot',   width: 110,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort}
 ];
gridWidthMap.put("cols142", 450); //width최소값

/**
 * 매출분석_시간대별
 */
var cols143 = [
   { label: '구분'  , name: 'div',   width:80,   align:'center' },
   { label: '시간'  , name: 'item',  width: 80,  align:'center', sorttype:datePopSort},  
   { label: '합계(원)'  , name: 'tot',   width: 130, align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort}
];
gridWidthMap.put("cols143", 290); //width최소값

/**
 * 매출분석_업종별
 */
var cols144 = [
   { label: '구분'   , name: 'div'  ,width:80,   align:'center'},
   { label: 'lcd'  , name: 'lclas' ,width: 0,  align:'center', hidden:true},
   { label: 'mcd'  , name: 'mclas' ,width: 0,  align:'center', hidden:true},
   { label: 'scd'  , name: 'item' ,width: 0,  align:'center', hidden:true},
   { label: '대분류'  , name: 'lnm' ,width: 80,  align:'center'},  
   { label: '중분류'  , name: 'mnm' ,width: 100,  align:'center'},
   { label: '소분류'  , name: 'snm' ,width: 90,  align:'center'},   
   { label: '소비매출(원)' , name: 'tot' ,width: 130,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort }
];
gridWidthMap.put("cols144", 480); //width최소값


/**
 * 매출분석_외국인
 */
var cols145 = [
   { label: '구분'   , name: 'div'  ,width:70,   align:'center'},
   { label: '국가'  , name: 'item' ,width: 100,  align:'center'},
   { label: 'emd'  , name: 'emd' ,width: 0,  align:'center', hidden:true},      
   { label: '이용건수(건)'  , name: 'cnt' ,width: 90,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort},   
   { label: '소비매출(원)' , name: 'tot' ,width: 120,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort}
];
gridWidthMap.put("cols145", 380); //width최소값

/**
 * 유입매출_시도별
 */
var cols151 = [
   { label: '구분'  , name: 'item',   width:100,   align:'center' },     
   { label: '유입매출(원)'  , name: 'cnt',   width: 130, align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort}
];
gridWidthMap.put("cols151", 230); //width최소값

/**
 * 유입매출_시군구별(서울,경기)
 */
var cols152 = [
   { label: '구분'  , name: 'item',   width:100,   align:'center' },     
   { label: '유입매출(원)'  , name: 'cnt',   width: 130, align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort}
];
gridWidthMap.put("cols152", 230); //width최소값

/**
 * 유입매출_화성시내(인접)
 */
var cols153 = [
   { label: '구분'  , name: 'item',   width:100,   align:'center' },     
   { label: '유입매출(원)'  , name: 'cnt',   width: 130, align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort}
];
gridWidthMap.put("cols153", 230); //width최소값



////////////////////////////////////////////////////////////////////////////////
///////////////////////// 교통 ///////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

/**
 * 대중교콩 모델 사각지대분석
 */
var cols211 = [              
   { label: 'mid'   , name: 'mid',  width: 0, hidden:true,   formatter:function(){return "2-1-1";} },   
   { label: 'item', name: 'item', width: 0,  align:'center', hidden:true },   
   { label: '격자ID'   , name: 'div',  width: 100,  align:'center', sorttype:'number'},
   { label: '위치확인', name: 'cst', width: 100,  align:'center', formatter:cstLoc },
   { label: '주민등록인구수(명)'  , name: 'pop',  width: 130,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '유동인구수(명)'  , name: 'fpop',  width: 130,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} }
];
gridWidthMap.put("cols211", 460); //width최소값

/**
 * 교통 저상버스도입요구분석
 */
var cols221 = [              
   { label: 'mid'    , name: 'mid',  width: 0, hidden:true,   formatter:function(){return "2-2-1";} },   
   { label: 'lnm'    , name: 'lid',  width: 0,  align:'center', hidden:true },      
   { label: '버스노선'  , name: 'div',  width: 70,  align:'center' , sorttype:datePopSort},
   { label: '화면표시'  , name: 'cst', width: 70,  align:'center', formatter:cstBusLine },   
   { label: '노인인구(명)'  , name: 'opop',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '유동인구(명)'  , name: 'fpop',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '시청/구청(개)' , name: 'cityhall',  width: 90,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '병원(개)'   , name: 'hospital',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '보건소(개)'  , name: 'medical',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '복지시설(개)' , name: 'woffice',  width: 85,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '노인인구 백분위(%)'  , name: 'old100',  width: 110,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '시설 백분위(%)', name: 'fac100',  width: 90,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '백분위 합계(%)', name: 'tot',  width: 90,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
];
gridWidthMap.put("cols221", 925); //width최소값


/**
 * 교통 교통카드 현황분석
 */
var cols231 = [
   { label: 'mid'   , name: 'mid',  width: 0, hidden:true,   formatter:function(){return "2-3-1";} },   
   { label: 'div'    , name: 'div',   width: 0,  align:'center', hidden:true },
   { label: 'geom'    , name: 'geom',  width: 0,   align:'center', hidden:true },
   { label: 'sid'     , name: 'sid',   width: 0,   align:'center', hidden:true },
   { label: 'lid'     , name: 'lid',   width: 0,   align:'center', hidden:true },
   { label: '정류장'    , name: 'cst',   width: 130,  align:'center', formatter:cstStop},
   { label: '승차(명)'  , name: 'spop',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '하차(명)'  , name: 'fpop',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort },
   { label: '환승(명)'  , name: 'tpop',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort }
];
gridWidthMap.put("cols231", 370); //width최소값


/**
 * 교통 교통카드 현황분석
 */
var cols232 = [
   { label: 'mid'   , name: 'mid',  width: 0, hidden:true,   formatter:function(){return "2-3-2";} },
   { label: '정류장'   , name: 'div',  width: 60,  align:'center' },      
   { label: '06:00'  , name: 't06',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort  },
   { label: '07:00'  , name: 't07',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort  },
   { label: '08:00'  , name: 't08',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort  },
   { label: '08:00'  , name: 't08',  width: 70,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort  }
];
gridWidthMap.put("cols232", 340); //width최소값


/**
 * 교통 정류장별 승하차 정보
 */
var cols241 = [        
   { label: 'mid'   , name: 'mid',  width: 0, hidden:true,   formatter:function(){return "2-4-1";} },      
   { label: 'geom'    , name: 'geom',  width: 0,   align:'center', hidden:true },   
   { label: 'lid'     , name: 'lid',   width: 0,   align:'center', hidden:true },
   { label: '정류장ID'   , name: 'sid',  width: 70,  align:'center' },
   { label: '화면표시',   name: 'cst', width: 70,  align:'center', formatter:cstBusStop },   
   { label: '정류장명', name: 'div', width: 120,  align:'center' },
   { label: '승객수(명)'  , name: 'pop',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort  },
   { label: '승차인원(명)'  , name: 'spop',  width: 80,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort  },
   { label: '승차환승인원(명)'  , name: 'tpop',  width: 110,  align:'center', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}, sorttype:datePopSort  }
];
gridWidthMap.put("cols241", 530); //width최소값


/**
 * 교통 탄력배치분석 (요일별 승차인원)
 */
var cols251 = [         
   { label: 'mid'   , name: 'mid',  width: 0, hidden:true,   formatter:function(){return "2-5-1";} },   
   { label: 'lid'   , name: 'lid',  width: 0,  align:'center', hidden:true },
   { label: '버스노선'   , name: 'div',  width: 60,  align:'center' , sorttype:'number'},
   { label: '화면표시'   , name: 'cst',  width: 60,  align:'center', formatter:cstBusLine },   
   { label: '월(명)'  , name: 'd1', width: 70,  align:'center' },   
   { label: '화(명)'  , name: 'd2', width: 70,  align:'center' },
   { label: '수(명)'  , name: 'd3',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '목(명)'  , name: 'd4',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '금(명)'  , name: 'd5',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '토(명)'  , name: 'd6',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '일(명)'  , name: 'd7',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '승차인원(A)(명)'  , name: 'd8',  width: 100,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '주말승차(B)(명)'  , name: 'd9',  width: 100,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '비율(B/A)(%)'  , name: 'd10',  width: 100,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} }
];
gridWidthMap.put("cols251", 910); //width최소값


/**
 * 교통 탄력배치분석 (시간별 승차인원)
 */
var cols252 = [
   { label: 'mid'   , name: 'mid',  width: 0, hidden:true,   formatter:function(){return "2-5-2";} },   
   { label: 'lid'   , name: 'lid',  width: 0,  align:'center', hidden:true },
   { label: '버스노선' , name: 'div',  width: 70,  align:'center' , sorttype:'number'},
   { label: '화면표시'   , name: 'cst',  width: 70,  align:'center', formatter:cstBusLine },   
   { label: '00시(명)'  , name: 'p00',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '01시(명)'  , name: 'p01',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '02시(명)'  , name: 'p02',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '03시(명)'  , name: 'p03',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '04시(명)'  , name: 'p04',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '05시(명)'  , name: 'p05',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '06시(명)'  , name: 'p06',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '07시(명)'  , name: 'p07',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '08시(명)'  , name: 'p08',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '09시(명)'  , name: 'p09',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '10시(명)'  , name: 'p10',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '11시(명)'  , name: 'p11',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '12시(명)'  , name: 'p12',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '13시(명)'  , name: 'p13',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '14시(명)'  , name: 'p14',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '15시(명)'  , name: 'p15',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '16시(명)'  , name: 'p16',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '17시(명)'  , name: 'p17',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '18시(명)'  , name: 'p18',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '19시(명)'  , name: 'p19',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '20시(명)'  , name: 'p20',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '21시(명)'  , name: 'p21',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '22시(명)'  , name: 'p22',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '23시(명)'  , name: 'p23',  width: 70,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   
   { label: '총 승차인원(A)(명)'  , name: 'tot',  width: 110,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '첨두시간 승차인원(B)(명)'  , name: 'peak',  width: 140,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} },
   { label: '비율(B/A)(%)'  , name: 'rat',  width: 100,  align:'center', formatter:'currency', sorttype:'number', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} }
];
gridWidthMap.put("cols252", 2170); //width최소값


/**
 * 통행패턴 정보
 */
var cols271 = [        
   { label: 'mid'   , name: 'mid',  width: 0, hidden:true,   formatter:function(){return "2-7-1";} },      
   { label: 'fp'    , name: 'fp',  width: 0,   align:'center', hidden:true },   
   { label: 'min'    , name: 'min',  width: 0,   align:'center', hidden:true },
   { label: 'max'    , name: 'max',  width: 0,   align:'center', hidden:true },
   { label: 'fid'    , name: 'fid',  width: 0,   align:'center', hidden:true },
   { label: 'fnm'    , name: 'fnm',  width: 0,   align:'center', hidden:true },
   { label: '하차정류장'   , name: 'cst',  width: 100,  align:'center', formatter:cstPassStop},
   { label: '승객수(명)'  , name: 'pop',  width: 100,  align:'center', sorttype:'number', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0} }
];
gridWidthMap.put("cols271", 200); //width최소값

gridCols.push({
	"cols1-1-1"   : cols111, 
	"cols1-1-2"   : cols112,
	"cols1-1-3-1" : cols1131,
	"cols1-1-3-2" : cols1132,	
	"cols1-1-4-1" : cols1141,
	"cols1-1-4-2" : cols1142,
	"cols1-2-1"   : cols121,
	"cols1-2-2"   : cols122,
	"cols1-2-3"   : cols123,
	"cols1-4-1"   : cols141,
	"cols1-4-2"   : cols142,
	"cols1-4-3"   : cols143,
	"cols1-4-4"   : cols144,
	"cols1-4-5"   : cols145,
	"cols1-5-1"   : cols151,
	"cols1-5-2"   : cols152,
	"cols1-5-3"   : cols153,	
	"cols1-3-1"   : cols131,
	"cols1-3-2"   : cols132,
	"cols1-3-3"   : cols133,
	
	"cols2-1-1"   : cols211,
	"cols2-2-1"   : cols221,
	"cols2-3-1"   : cols231,
	"cols2-3-2"   : cols232,
	"cols2-4-1"   : cols241,
	"cols2-5-1"   : cols251,
	"cols2-5-2"   : cols252,
	"cols2-7-1"   : cols271
});


/**
 * custom date정보로 변경
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns {String}
 */
function cstItemDate(cellvalue, options, rowObject){
	var item = rowObject.item;
	if(item != undefined && item != null && 
			item != '합계' && item != '' && item != '전체')
		item = item.substring(2,4) + "년" + item.substring(4,6) +"월";
	return item; 
}

/**
 * sort 고정 item
 */
function datePopSort(cell, obj){
	if(obj.div == '전체' 
		|| obj.div == '합계'
		|| obj.item == '합계' 
		|| obj.age == '합계'
		|| obj.gb == '합계'
		|| obj.lnm == '합계'
		|| obj.sid == '합계')
		return null;
	else	
		return Number(cell);
}