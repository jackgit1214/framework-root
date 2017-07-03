/**
 * 通用文件管理组件
 * @author lilj
 * 1、实现根据连接预览图片：
 * 	  <1>需指定图片显示容器，<2> 图片显示格式
 * 
 * 2、弹出窗口上传图片
 * 3、支持定制默认图片格式
 * 4、
 */

(function ($, window, document, undefined) {
    'use strict';

    var pluginName = 'fileInputCustom';
    var pluginParam={
    		busiDataUrl:"http://localhost:8080/resources/public/getAttaData",
    		attachmentType:{video: '0', image: '1', audio: '2', doc: '3',other:'4' }
    };
    var options = {
    		frameClass:'',
    		previewClass:'',
    		textEncoding:'GBK',
    		isCustomLayoutTemplates:true,
    		previewWith:'80px',
    		perviewHeight:'80px',
    		window:false,
    		windowWidth:900,
    		isReadOnly:false,
            uploadUrl: "http://localhost:8080/resources/resources/update",
            deleteUrl:"http://localhost:8080/resources/resources/delete",
            multiple: true
    };
    $.fn[pluginName] = function (options) {
        var self = $(this);
        if (this == null)
            return null;
        var args = Array.apply(null, arguments), retvals = [];
        args.shift();
        var data = this.data(pluginName);
        if (!data) {
            data = new FileInputCustom(this, $.extend(true, {}, options));
            self.data(pluginName, data);
        }
	    if (typeof options === 'string') {
	    	retvals.push(data[options].apply(data, args));
	    }
    };


    var FileInputCustom = function (element, options) {
        this.element = element;

        options.container = options.container || this.element.selector.replace("#", "");
        //初始化文件图标信息
        this.settings = $.extend(true, {}, this.init, options);
        //初始化图标信息
       
        this._setFileInputParam();
        this.winDialog = this._initAttachmentWin();
        
        if(this.settings.window) {
            this.element.click(function () {
                $(this).data('fileInputCustom').openWin();
            });
        }else{
            //非弹窗形式
            if(this.settings.multiple)
                this.element.attr("multiple","multiple");
        }

      
        //如果配置了附件编辑容器showContainer（附件列表，可单个删除），则进行初始化
//        if(this.hasDisplayZone()){
//            this.showFiles();
//        };
    };
    FileInputCustom.prototype = { 
    	//这里定义的参数，不允许修改	
    	init:{
    		title: "文件上传",
            url: "/museum/public/widget/uploader",
            fileinput: {
                language: 'zh',
                uploadAsync:false,
                validateInitialCount:true,
                overwriteInitial: false,
                showRemove:false,
                showUpload:false,
                showClose:false,
                showBrowse:true,
                showCaption:true,
                previewFileIconSettings: { // configure your icon file extensions
                        'doc': '<i class="fa fa-file-word-o text-primary"></i>',
                        'xls': '<i class="fa fa-file-excel-o text-success"></i>',
                        'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
                        'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
                        'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
                        'htm': '<i class="fa fa-file-code-o text-info"></i>',
                        'txt': '<i class="fa fa-file-text-o text-info"></i>',
                        'mov': '<i class="fa fa-file-movie-o text-warning"></i>',
                        'mp3': '<i class="fa fa-file-audio-o text-warning"></i>',
                        'jpg': '<i class="fa fa-file-photo-o text-danger"></i>', 
                        'JPG': '<i class="fa fa-file-photo-o text-danger"></i>', 
                        'gif': '<i class="fa fa-file-photo-o text-muted"></i>', 
                        'png': '<i class="fa fa-file-photo-o text-primary"></i>'    
                 },
                initialPreviewAsData: true,
                allowedPreviewTypes:['image', 'html', 'text', 'video', 'audio', 'flash', 'object'],
               // allowedPreviewTypes: ['image'],
               // previewFileIcon:'<i class="fa fa-file-o"></i>',
                previewClass:"filepreview-custom",
                slugCallback: function (text) {
                    var newtext=(!text||text=='') ? '' : String(text).replace(/[\-\[\]\/\{}:;#%=\(\)\*\+\?\\\^\$\|<>&"']/g, '_');
                    //去除空格
                    return newtext.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
                },
                layoutTemplates:{
                	actions:'<div class="filepreview-action">{delete}</div>', //不显示功能区域
                	actionDelete:'<button type="button" class="kv-file-remove btn btn-xs btn-default" style="border:0px;" ' +
                    'title="{removeTitle}" {dataUrl}{dataKey}>{removeIcon}</button>\n',
                    
                    actionDrag:'',
                	footer: '<div class="file-thumbnail-footer">\n' +
                    '  <div class="file-footer-caption" title="{caption}"><div class="radio m-t-0 m-b-0 chk-preview"> <label class="p-0"> <input type="radio" value="0" name="radio">{caption} </label></div></div>\n</div>{actions}'//只显示文件名信息
                }
            }
    	},
    	getFileInputConfig:function(){
    		return this.settings.fileinput;
    	},
    	_setFileInputParam:function(){
    		var width = options.previewWith;
    		var height = options.perviewHeight;
    		var $that = this;
    		this.settings.fileinput.previewSettings={
                image: {width: width, height: height},
                html: {width: width, height:  height},
                text: {width:  width, height: height},
                video: {width: width, height: height},
                audio: {width: width, height: height},
                flash: {width: width, height: height},
                object: {width: width, height: height},
                pdf: {width: width, height: height},
                other: {width: width, height: height}
            };
    		//uploadExtraData:{dataid:'${dataid}', busstype:'${busiType}',attano:'1'},
    		var dataids = this.settings.data.dataIds;
    		var busitype = this.settings.data.busiType;
    		this.settings.fileinput.uploadExtraData = {dataid:dataids,busstype:busitype,attano:'1'};
    		this.settings.fileinput.uploadUrl = options.uploadUrl;
    		this.settings.fileinput.deleteUrl= options.deleteUrl;
            this.settings.fileinput.ajaxDeleteSettings ={
            	complete:function(){
            		setTimeout(function () {
            			$that.updateFileContainer();
                    }, 1000);
            	}
            };
    	},
    	_initAttachmentWin:function(){
    		var $that = this;
    		 var self = $.extend(true, {}, this.settings);
    		var $dialogInputFile = customModals.popupDialog({
    			data : self.data,
    			height : 600,
    			width : 800,
    			title : "附件上传",
    			show:false,
    			callback:function(){
    				
    			   $('#acctype').collapse();
    			},
    			pageCallback:function(){
    				$that.fileComponet = $that._initFileInputCom();
    				
    				$that.showFiles();
    			}
    		},$that.settings.url);
    		return $dialogInputFile;
    	},
    	openWin:function () {
            this.winDialog.modal('show');
        },
        closeDialog:function(){
        	 this.winDialog.modal('hide');
        },
        destroyDialog:function(){
        	this.winDialog.remove();
        },
        _createModalContent:function(){
        	//内部创建弹出窗口的html内容
        },
        
        _initFileInputConfig:function(fileData){
        	 var config=null;
           
            if(this.settings.window&&fileData){
                if(this.settings.showType=="edit"){
                    config=$.extend({},this.settings.fileinput,{
                    	initialPreview:fileData.initData, //初始化数据
                        initialPreviewConfig:fileData.initDataCon//初始化数据配置
                    });
                }else if(this.settings.showType=="detail"){ //仅预览时
                    config=$.extend({},this.settings.fileinput,{
                    	initialPreview:fileData.initData, //初始化数据
                        initialPreviewConfig:fileData.initDataCon//初始化数据配置
                    });
                }
            }else{
                config=$.extend({},this.settings.fileinput,{
                    showClose:false
                });
            }
            return config;
        },
        showFiles:function(){
        	var dataIds=options.dataIds||this.settings.data.dataIds;
        	if(!dataIds&&this.settings.window){
                $(this.settings.showContainer).hide();
                 return;
             }
        	this.typeFilter(dataIds);
        	
        },
        upload:function(){
        	
        	this.fileComponet.fileinput("upload");
        },
        refreshData:function(param){
        	var dataIds=options.dataIds||this.settings.data.dataIds;
        	if(!dataIds&&this.settings.window){
                $(this.settings.showContainer).hide();
                 return;
             }
        	this.typeFilter(dataIds, param.type, true);
        },
        /**
         * 根据指定业务数据的id,取得文件数据，
         * 如果指定了附件类型，则取指定数据的所有指定类型的附件。
         * @param dataId,当前数据ID，
         * @param type 显示的附件类型
         * @param isRefresh 是否是过滤
         * @returns
         */
        typeFilter:function(dataId,type,isRefresh){
        	
        	        	
        	var dataurl = pluginParam.busiDataUrl+"/"+dataId;

			if (type)
				dataurl = dataurl+"/"+type;
			
			var $this =this;
			$.getJSON(dataurl,{},function(data){
	    		
	    		var initData =[];
	        	var initDataCon=[];
	    		var attachments = data.attachments;
	    		$.each(attachments,function(i,attachment){
	    			var dataCon = {caption:attachment.filename,size:attachment.filesize,width:'80px',key:attachment.attaid,height:'90px',extra:{ids:[attachment.attaid]}};
	    			if (attachment.attatype=="1"){
	    				initData.push("/resources/"+"public/image/"+attachment.attaid+"?permission=3");
	    				$.extend(dataCon,{type:"image"});
	    			}else{
	    				
	    				initData.push("http://localhost:8080/resources/public/other/"+attachment.attaid);
	    				$.extend(dataCon,{type:"object"});
	    			}
	    			
	    			initDataCon.push(dataCon);
	    		});
	    		$this.fileinputShow(initData,initDataCon,isRefresh);
	    		$('.chk-preview').iCheck({
		            checkboxClass: 'icheckbox_minimal',
		            radioClass: 'iradio_minimal',
		            increaseArea: '20%' // optional
		        });
	    		$this._bindRadioEvent();
				
	    	});
		},
		_bindRadioEvent:function(){

			var $thumb = this.parentContainer.find(".file-preview-thumbnails").children().not(".kv-zoom-cache");
			var $chk = $thumb.find(".radio");
			
			$chk.on("click",function(){
				
				var $that = $(this);
				//$chk.find("input").val(0);
				//$that.val(1);
			});
		},
		_initFileInputCom:function(){
			var $this = this;
			var $fileComponet=$(this.settings.showContainer);
			 var $comP = $fileComponet.parent();
			 $this.parentContainer = $comP;
			var config = this._initFileInputConfig();
			$fileComponet.fileinput('destroy');
			
			$fileComponet.fileinput(config).on("fileremoved",function(event, id, index){
				$this.updateFileContainer();
			}).on('filedeleted', function(event, key, jqXHR, data) {
				setTimeout(function(){
					 $this.updateFileContainer();
				},1000);
			  
			}).on('filebatchselected', function(event, files) {
		        $('.chk-preview').iCheck({
		            checkboxClass: 'icheckbox_minimal',
		            radioClass: 'iradio_minimal',
		            increaseArea: '20%' // optional
		        });
			}).on('filebatchuploadcomplete', function(event, files, extra) {
				$this.showFiles(); //重新刷新下，否则不能新增加的附件不能删除。
//				setTimeout(function(){
//					$fileComponet.fileinput("reset"); //重新刷新下，否则不能新增加的附件不能删除。
//				},600);
			}).on('filebatchpreupload', function(event, data, previewId, index) {
				//这里可上传每个文件的数据，例如是否缺省
				var form = data.form, files = data.files, extra = data.extra;
				
				var $thumb = $comP.find(".file-preview-thumbnails").children().not(".kv-zoom-cache");
				
				var $chk = $thumb.find("input[type=radio]");
				var chkvalue = [$chk.length];
				$.each($chk,function(i,obj){
					var isCheck = $(obj).is(':checked');
					if (isCheck)
						chkvalue[i]=1;
					else
						chkvalue[i]=0;
				});
				form.append("isDefault",chkvalue);
			});
//			$fileComponet.fileinput(config).on("filedeleted",function (event,key) {
//            	$this.updateFileContainer();
//            }).on("fileuploaded",function(event,data,previewId,index){
//            	$this.updateFileContainer();
//            }).on("fileloaded",function(){
//            	$this.updateFileContainer();
//            })
//            .on("filebatchuploadsuccess",function (event,data,previewId,index) {
//           	 	$this.updateFileContainer();
//            });

			$comP.on('dblclick','.kv-preview-thumb > .kv-file-content',function(){
    			var $that = $(this);
    			var previewid = $that.parent().attr("id");
    			$fileComponet.fileinput('zoom', previewid);
    		});		
			

			return $fileComponet;
			
		},
		fileinputShow:function(initData,initDataCon,isRefresh){
            //显示
			this.fileComponet.fileinput("resetData",{initialPreview:initData,initialPreviewConfig:initDataCon});
            if (isRefresh){
            	
            	return; 
            }
            this.updateFileContainer();
		},
		updateFileContainer:function(){
			if (this.settings.window && !this.settings.fileListContainer)
				return ;
			var fileCon = $(this.settings.fileListContainer); 
			var $filepreview = $(".file-preview").clone();
			$filepreview.find(".kv-file-remove").remove();
			$filepreview.find(".chk-preview").remove();
			var filecount = this.fileComponet.fileinput("getFilesCount");
			if (filecount <=0){
				$filepreview.find(".file-drop-zone-title").html("<br/>无附件<br/>");
			}
			fileCon.html($filepreview.html());
		}
    };

    FileInputCustom.prototype.callbackHandler = function (fileIds) {
        //更新fileIds并执行回调函数
        var oldfileIds = this.settings.fileIds;
        this.settings.fileIds = fileIds;
        this.updateFileIds();
        if (this.settings.callback) {
            this.settings.callback.call(this, fileIds, oldfileIds);
        }
    };

 
    /**
     * 是否有显示区域
     * @returns {boolean}
     */
    FileInputCustom.prototype.hasDisplayZone=function(){
        if(!this.settings.showContainer){
           this.settings.showContainer=this.element.selector;
        }
        if(!this.settings.showContainer||!$(this.settings.showContainer)){
            return false;
        }
        return true;
    };

})(jQuery, window, document);