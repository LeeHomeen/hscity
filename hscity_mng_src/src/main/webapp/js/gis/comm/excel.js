
/**
 * 엑셀 parameter 생성
 * @param id
 * @returns
 */
function getExcelParams(id){
	var params = [];
	var gridId = "#tblGrid"+id;
	 var ids = $(gridId).jqGrid('getDataIDs');
     for(var i=0; i < ids.length; i++){
         var ret = $(gridId).jqGrid('getRowData', ids[i]);         
         if(id == '1-1-1'){        	 
        	 var obj = "{'div':'"+ret.div+"','cst':'"+ret.cst+"','a00':'"+ret.a+"',";
        	 obj += "'a10':'"+ret.b+"','a20':'"+ret.c+"','a30':'"+ret.d+"','a40':'"+ret.e+"',";
             obj += "'a50':'"+ret.f+"','a60':'"+ret.g+"','a70':'"+ret.h+"','a80':'"+ret.i+"',";
             obj += "'a90':'"+ret.j+"','a100':'"+ret.k+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-1-2'){
        	 var obj = "{'div':'"+ret.div+"','age':'"+ret.age+"','mcnt':'"+ret.mcnt+"',";
        	 obj += "'wcnt':'"+ret.wcnt+"','tot':'"+ret.tot+"'}";
             params.push(obj);
         }else if(id == '1-1-3-1'){
        	 var obj = "{'item':'"+ret.item+"','cnt':'"+ret.cnt+"'}";
             params.push(obj);
         }else if(id == '1-1-3-2'){
        	 var obj = "{'item':'"+ret.item+"','cnt':'"+ret.cnt+"'}";
             params.push(obj);
         }else if(id == '1-1-4-1'){
        	 var obj = "{'item':'"+ret.item+"','cnt':'"+ret.cnt+"'}";
             params.push(obj);
         }else if(id == '1-1-4-2'){
        	 var obj = "{'item':'"+ret.item+"','cnt':'"+ret.cnt+"'}";
             params.push(obj);
         }else if(id == '1-2-1'){
        	 var obj = "{'div':'"+ret.div+"','cst':'"+ret.cst+"','p10':'"+ret.p10+"',";
        	 obj += "'p20':'"+ret.p20+"','p30':'"+ret.p30+"','p40':'"+ret.p40+"','p50':'"+ret.p50+"',";
             obj += "'p60':'"+ret.p60+"','p70':'"+ret.p70+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-2-2'){
        	 var obj = "{'div':'"+ret.div+"','m10':'"+ret.m10+"',";
        	 obj += "'m20':'"+ret.m20+"','m30':'"+ret.m30+"','m40':'"+ret.m40+"','m50':'"+ret.m50+"',";
        	 obj += "'m60':'"+ret.m60+"','m70':'"+ret.m70+"','w10':'"+ret.w10+"','w20':'"+ret.w20+"',";
        	 obj += "'w30':'"+ret.w30+"','w40':'"+ret.w40+"','w50':'"+ret.w50+"','w60':'"+ret.w60+"',";
             obj += "'w70':'"+ret.w70+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-2-3'){
        	 var obj = "{'div':'"+ret.div+"','t00':'"+ret.t00+"',";
        	 obj += "'t01':'"+ret.t01+"','t02':'"+ret.t02+"','t03':'"+ret.t03+"','t04':'"+ret.t04+"',";
        	 obj += "'t05':'"+ret.t05+"','t06':'"+ret.t06+"','t07':'"+ret.t07+"','t08':'"+ret.t08+"',";
        	 obj += "'t09':'"+ret.t09+"','t10':'"+ret.t10+"','t11':'"+ret.t11+"','t12':'"+ret.t12+"',";        	 
        	 obj += "'t13':'"+ret.t13+"','t14':'"+ret.t14+"','t15':'"+ret.t15+"','t16':'"+ret.t16+"',";
        	 obj += "'t17':'"+ret.t17+"','t18':'"+ret.t18+"','t19':'"+ret.t19+"','t20':'"+ret.t20+"',";        	 
             obj += "'t21':'"+ret.t21+"','t22':'"+ret.t22+"','t23':'"+ret.t23+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-3-1'){
        	 var obj = "{'div':'"+ret.div+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-3-2'){
        	 var obj = "{'div':'"+ret.div+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-3-3'){
        	 var obj = "{'div':'"+ret.div+"','m10':'"+ret.m10+"',";
        	 obj += "'m20':'"+ret.m20+"','m30':'"+ret.m30+"','m40':'"+ret.m40+"','m50':'"+ret.m50+"',";
        	 obj += "'m60':'"+ret.m60+"','m70':'"+ret.m70+"','w10':'"+ret.w10+"','w20':'"+ret.w20+"',";
        	 obj += "'w30':'"+ret.w30+"','w40':'"+ret.w40+"','w50':'"+ret.w50+"','w60':'"+ret.w60+"',";
             obj += "'w70':'"+ret.w70+"','tot':'"+ret.tot+"'}";
             params.push(obj);
         }else if(id == '1-4-1'){
        	 var obj = "{'div':'"+ret.div+"','cst':'"+ret.cst+"','apop':'"+ret.apop+"',";
        	 obj += "'bpop':'"+ret.bpop+"','cpop':'"+ret.cpop+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-4-2'){
        	 var obj = "{'div':'"+ret.div+"','gb':'"+ret.gb+"','mcnt':'"+ret.mcnt+"',";
        	 obj += "'wcnt':'"+ret.wcnt+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-4-3'){
        	 var obj = "{'div':'"+ret.div+"','item':'"+ret.item+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-4-4'){
        	 var obj = "{'div':'"+ret.div+"','lnm':'"+ret.lnm+"','mnm':'"+ret.mnm+"',";
        	 obj += "'snm':'"+ret.snm+"','tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-4-5'){
        	 var obj = "{'div':'"+ret.div+"','item':'"+ret.item+"','cnt':'"+ret.cnt+"',";
        	 obj += "'tot':'"+ret.tot+"'}";
        	 params.push(obj);
         }else if(id == '1-5-1'){
        	 var obj = "{'item':'"+ret.item+"','cnt':'"+ret.cnt+"'}";
             params.push(obj);
         }else if(id == '1-5-2'){
        	 var obj = "{'item':'"+ret.item+"','cnt':'"+ret.cnt+"'}";
             params.push(obj);
         }else if(id == '1-5-3'){
        	 var obj = "{'item':'"+ret.item+"','cnt':'"+ret.cnt+"'}";
             params.push(obj);
         }
         //교통
         else if(id == '2-2-1'){
        	 var obj = "{'div':'"+ret.div+"',";
        	 obj += "'opop':'"+ret.opop+"','fpop':'"+ret.fpop+"','cityhall':'"+ret.cityhall+"','hospital':'"+ret.hospital+"',";
        	 obj += "'medical':'"+ret.medical+"','woffice':'"+ret.woffice+"','old100':'"+ret.old100+"','fac100':'"+ret.fac100+"',";
        	 obj += "'tot':'"+ret.tot+"'}";
             params.push(obj);
         }else if(id == '2-3-1'){
        	 var obj = "{'div':'"+ret.div+"','spop':'"+ret.spop+"',";
        	 obj += "'fpop':'"+ret.fpop+"','tpop':'"+ret.tpop+"'}";
             params.push(obj);
         }else if(id == '2-4-1'){
        	 var obj = "{'sid':'"+ret.sid+"','div':'"+ret.div+"','pop':'"+ret.pop+"',";
        	 obj += "'spop':'"+ret.spop+"','tpop':'"+ret.tpop+"'}";
        	 params.push(obj);
         }else if(id == '2-5-1'){
        	 var obj = "{'div':'"+ret.div+"','d1':'"+ret.d1+"',";
        	 obj += "'d2':'"+ret.d2+"','d3':'"+ret.d3+"','d4':'"+ret.d4+"','d5':'"+ret.d5+"',";
        	 obj += "'d6':'"+ret.d6+"','d7':'"+ret.d7+"','d8':'"+ret.d8+"','d9':'"+ret.d9+"',";        	 
             obj += "'d10':'"+ret.d10+"'}";
             params.push(obj);
         }else if(id == '2-5-2'){
        	 var obj = "{'div':'"+ret.div+"','p00':'"+ret.p00+"',";
        	 obj += "'p01':'"+ret.p01+"','p02':'"+ret.p02+"','p03':'"+ret.p03+"','p04':'"+ret.p04+"',";
        	 obj += "'p05':'"+ret.p05+"','p06':'"+ret.p06+"','p07':'"+ret.p07+"','p08':'"+ret.p08+"',";        	 
        	 obj += "'p09':'"+ret.p09+"','p10':'"+ret.p10+"','p11':'"+ret.p11+"','p12':'"+ret.p12+"',";
        	 obj += "'p13':'"+ret.p13+"','p14':'"+ret.p14+"','p15':'"+ret.p15+"','p16':'"+ret.p16+"',";
        	 obj += "'p17':'"+ret.p17+"','p18':'"+ret.p18+"','p19':'"+ret.p19+"','p20':'"+ret.p20+"',";
        	 obj += "'p21':'"+ret.p21+"','p22':'"+ret.p22+"','p23':'"+ret.p23+"','tot':'"+ret.tot+"',";
        	 obj += "'peak':'"+ret.peak+"','rat':'"+ret.rat+"'}";
             params.push(obj);
         }else if(id == '2-7-1'){
        	 var obj = "{'fnm':'"+ret.fnm+"','pop':'"+ret.pop+"'}";
        	 params.push(obj);
         }
     }
     return JSON.stringify(params);
}