(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["executionConfig"],{b405:function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t._self._c;return e("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}]},[e("el-form",{ref:"formData",attrs:{model:t.formData,rules:t.formDataRules,"label-position":"right",size:"mini","label-width":"150px"}},[e("el-card",[e("div",{attrs:{slot:"header"},slot:"header"},[e("h4",[t._v("基本信息")])]),e("el-form-item",{attrs:{label:"请输入描述：",prop:"runDesc"}},[e("el-input",{attrs:{disabled:t.disabledFlag,size:"small"},model:{value:t.formData.runDesc,callback:function(e){t.$set(t.formData,"runDesc",e)},expression:"formData.runDesc"}})],1)],1),e("el-card",[e("div",{attrs:{slot:"header"},slot:"header"},[e("h4",[t._v("执行机器")])]),e("env-mechine",{ref:"envMechine",attrs:{formData:t.formData.form,detailFlag:t.disabledFlag}})],1)],1),e("el-card",{staticClass:"add-record-bottom-component"},[e("el-button",{attrs:{size:"small",type:"primary"},on:{click:t.execution}},[t._v("执行")]),e("el-button",{attrs:{size:"small"},on:{click:t.resetForm}},[t._v("取消")])],1)],1)},i=[],r=(a("a450"),a("0cdc"),a("f829")),n=a("365c"),o=a("d19b"),l={components:{envMechine:o["a"]},data:function(){return{loading:!1,type:"",taskRunId:"",disabledFlag:!1,formData:{runDesc:"",form:null},formDataRules:{runDesc:[{required:!0,message:"请输入描述",trigger:"blur"}]}}},created:function(){this.taskRunId=this.$route.params.taskRunId,this.type=this.$route.params.type,this.templateId=this.$route.params.templateId,"detail"==this.type&&(this.disabledFlag=!0,this.getDetail())},methods:{getDetail:function(){this.loading=!0,n["a"].record.getTaskRunDetail({taskRunId:this.taskRunId}).then(function(t){Object(r["a"])(this,this),"SUCCESS"===t.code?(t.data.host.runEnv=t.data.runEnv,this.formData.form=t.data.host,this.formData.runDesc=t.data.runDesc,this.loading=!1):(this.loading=!1,this.$message.error("获取详情失败:"+t.msg))}.bind(this),function(t){Object(r["a"])(this,this),this.loading=!1,this.$message.error("获取详情失败:"+t.msg)}.bind(this))},changeEnv:function(){this.$refs.formData.clearValidate()},execution:function(){this.$refs.formData.validate(function(t){if(Object(r["a"])(this,this),!t)return!1;var e=this.$refs.envMechine.envMechineValid();if(e){var a=this.$refs.envMechine.form,s=a.runEnv,i=a.hostIp,o=a.sftpPort,l=a.passWord,d=a.userName,h={templateId:this.templateId,runDesc:this.formData.runDesc,runEnv:s,runHosts:{hostIp:i,sftpPort:o,passWord:l,userName:d}};this.loading=!0,n["a"].record.run(h).then(function(t){Object(r["a"])(this,this),this.loading=!1,"SUCCESS"===t.code?this.resetForm():this.$message.error("操作失败:"+t.msg)}.bind(this),function(t){Object(r["a"])(this,this),this.loading=!1,this.$message.error("获取详情失败:"+t.msg)}.bind(this))}}.bind(this))},resetForm:function(){this.$store.dispatch("page/close",{tagName:this.$route.name,notNext:!0}),this.routerPush({name:"record",params:{},query:{}})}}},d=l,h=a("e607"),c=Object(h["a"])(d,s,i,!1,null,null,null);e["default"]=c.exports}}]);