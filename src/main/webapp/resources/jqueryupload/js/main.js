/*
 * jQuery File Upload Plugin JS Example 8.9.1
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* global $, window */
(function (global) {
    var bit = /b$/;
    var si = {
        bits: ["B", "kb", "Mb", "Gb", "Tb", "Pb", "Eb", "Zb", "Yb"],
        bytes: ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"]
    };

    /**
     * filesize
     *
     * @method filesize
     * @param  {Mixed}   arg        String, Int or Float to transform
     * @param  {Object}  descriptor [Optional] Flags
     * @return {String}             Readable file size String
     */
    var filesize = function (arg) {
        var descriptor = arguments[1] === undefined ? {} : arguments[1];

        var result = [];
        var skip = false;
        var val = 0;
        var e = undefined,
            base = undefined,
            bits = undefined,
            ceil = undefined,
            neg = undefined,
            num = undefined,
            output = undefined,
            round = undefined,
            unix = undefined,
            spacer = undefined,
            suffixes = undefined;

        if (isNaN(arg)) {
            arg = '0';
            //throw new Error("Invalid arguments");
        }

        bits = descriptor.bits === true;
        unix = descriptor.unix === true;
        base = descriptor.base !== undefined ? descriptor.base : 2;
        round = descriptor.round !== undefined ? descriptor.round : unix ? 1 : 2;
        spacer = descriptor.spacer !== undefined ? descriptor.spacer : unix ? "" : " ";
        suffixes = descriptor.suffixes !== undefined ? descriptor.suffixes : {};
        output = descriptor.output !== undefined ? descriptor.output : "string";
        e = descriptor.exponent !== undefined ? descriptor.exponent : -1;
        num = Number(arg);
        neg = num < 0;
        ceil = base > 2 ? 1000 : 1024;

        // Flipping a negative number to determine the size
        if (neg) {
            num = -num;
        }

        // Zero is now a special case because bytes divide by 1
        if (num === 0) {
            result[0] = 0;

            if (unix) {
                result[1] = "";
            } else {
                result[1] = "B";
            }
        } else {
            // Determining the exponent
            if (e === -1 || isNaN(e)) {
                e = Math.floor(Math.log(num) / Math.log(ceil));
            }

            // Exceeding supported length, time to reduce & multiply
            if (e > 8) {
                val = val * (1000 * (e - 8));
                e = 8;
            }

            if (base === 2) {
                val = num / Math.pow(2, e * 10);
            } else {
                val = num / Math.pow(1000, e);
            }

            if (bits) {
                val = val * 8;

                if (val > ceil) {
                    val = val / ceil;
                    e++;
                }
            }

            result[0] = Number(val.toFixed(e > 0 ? round : 0));
            result[1] = si[bits ? "bits" : "bytes"][e];

            if (!skip && unix) {
                if (bits && bit.test(result[1])) {
                    result[1] = result[1].toLowerCase();
                }

                result[1] = result[1].charAt(0);

                if (result[1] === "B") {
                    result[0] = Math.floor(result[0]);
                    result[1] = "";
                } else if (!bits && result[1] === "k") {
                    result[1] = "K";
                }
            }
        }

        // Decorating a 'diff'
        if (neg) {
            result[0] = -result[0];
        }

        // Applying custom suffix
        result[1] = suffixes[result[1]] || result[1];

        // Returning Array, Object, or String (default)
        if (output === "array") {
            return result;
        }

        if (output === "exponent") {
            return e;
        }

        if (output === "object") {
            return { value: result[0], suffix: result[1] };
        }

        return result.join(spacer);
    };

    // CommonJS, AMD, script tag
    if (typeof exports !== "undefined") {
        module.exports = filesize;
    } else if (typeof define === "function") {
        define(function () {
            return filesize;
        });
    } else {
        global.filesize = filesize;
    }
})(typeof global !== "undefined" ? global : window);

function isIE () {
    var myNav = navigator.userAgent.toLowerCase();
    return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
}

function getFileSize(fileInput) {
    try {
        var fileSize = 0;
        //for IE
        if (oldIE) {
            //before making an object of ActiveXObject,
            //please make sure ActiveX is enabled in your IE browser
            var objFSO = new ActiveXObject("Scripting.FileSystemObject");
            var filePath = fileInput.value;
            //console.log("File path: " + filePath);
            var objFile = objFSO.getFile(filePath);
            fileSize = objFile.size; //size in kb
        }
        //for FF, Safari, Opeara and Others
        else {
            fileSize = $("#" + fileid)[0].files[0].size //size in kb
        }
        return fileSize;
    }
    catch (e) {
        alert(e);
        return -1;
    }
}

//var uploadtimer;
var oldIE = false;
var DROP_FILE = "Drop file here";
var uploadURL;
var ju_maxsize = 0; // 총 용량 제한, 디폴트: 무제한
var maxsize = 1.5 * 1024 * 1024 * 1024; // 파일 용량 제한, 디폴트 값: 1.5GB
var fileGbn = ['xls', 'xlsx', 'doc', 'docx', 'hwp', 'txt', 'pdf', 'zip', 'ppt', 'pptx', 'gif', 'jpg', 'bmp', 'jpeg', 'png', 'alz', 'mov', 'mp4', 'mp3', 'avi']; // 허용 파일 확장자
var uploadResult = [];
var fileUploadCount = 0;
var sizelimit = false;

function updateSumSize(sizeInt, notUpdate){
    var sumsizeElm = $("#sumsize");

    if (sumsizeElm.length > 0) {
        /*if (size == '') {
            size = sumsizeElm.html();
        }
        sumsizeElm.html('총: ' + bytesToSize(size) + ' / ' + bytesToSize(ju_maxsize));
        sumsizeElm.removeClass("fade");*/
        //alert(sizeInt);
        var size = 0;
        if (sizeInt == '') {
            size = 0;
        } else {
            size = sizeInt;
        }

        if (notUpdate) {
            var text = sumsizeElm.text();
            //alert(text);
            if (text.indexOf('총:') >= 0) {
                //alert(text.substring(text.indexOf('총:') + 2, text.indexOf('/')).trim());
                var end = text.length;
                if (text.indexOf('/') > 0) {
                    end = text.indexOf('/');
                }
                size += getSizeReal(text.substring(text.indexOf('총:') + 2, end).trim());
            }

        } else {
            var elms = $('#fileupload').find(".size");
            var i = 0;

            for (i=0; i < elms.length; i++) {
                size += getSizeReal($(elms[i]).text());
            }
        }
        var sumstr = '총: ' + filesize(size);
        if(sizelimit) {
            sumstr = sumstr + ' / ' + filesize(ju_maxsize);
        }
        sumsizeElm.html(sumstr);
        sumsizeElm.removeClass("fade");
    }
}

function getSizeReal(text) {
    if (text == null || text == 'undefined'){
        return 0;
    }
    text = text.replace(/['"]+/g, '');
    var strs = text.split(" ");
    if (strs.length != 2) {
        return 0;
    }
    var c = 0;
    //KMGTPE
    if (strs[1] == "B") {
        c = 0;
    } else if (strs[1] == "KB") {
        c = 1;
    } else if (strs[1] == "MB") {
        c = 2;
    } else if (strs[1] == "GB") {
        c = 3;
    } else if (strs[1] == "TB") {
        c = 4;
    } else if (strs[1] == "PB") {
        c = 5;
    } else if (strs[1] == "EB") {
        c = 6;
    }

    return parseFloat(strs[0]) * Math.pow(1024, c);
}

function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

function deletefile(elm, fileName) {
    var tmp = $("#delete_files").val();
    if (tmp == null || tmp == "") {
        tmp = '"' + fileName +'"';
    } else {
        tmp = tmp + ', "' + fileName +'"';
    }

    $("#delete_files").val(tmp);
    var parentElm = $(elm).closest("tr");
    parentElm.remove();

    var body = $("#dropzone tbody");
    if(body.find("tr").length == 0) {
        body.append("<tr id='dropzone_holder' ><td colspan='3'>" + DROP_FILE + "</td></tr>");
    }
    updateSumSize('', false);
}

function checkAddedFiles(elm) {
    var obj = $("#fileupload").find(".template-upload");
    if (obj.length > 1) {
        $("#new_file_add").val(1);
    } else {
        $("#new_file_add").val(0);
    }

    var body = $("#dropzone tbody");
    if(body.find("tr").length == 1) {
        body.append("<tr id='dropzone_holder' ><td colspan='3'>" + DROP_FILE +"</td></tr>");
    }
    var size = $(elm).closest("tr").find(".size").html();
    var realSize = getSizeReal(size);
    updateSumSize(realSize*(-1), false);
}

function defaultSubmitForm() {
    var newFileAdded = $("#new_file_add").val();
    if (newFileAdded == 0) {
        var obj = getForm();
        var tmp = obj.delete_files.value;
        if (tmp != null && tmp != "") {
            obj.delete_files.value = encodeURIComponent('[' + tmp + ']');
            console.log("obj.delete_files.value::"+obj.delete_files.value);
        }
        obj.submit();
    }
}

function disableFormSubmit() {
    return false;
}

function submitForm() {
    var jsonObj;
    var jsonStr ='[';
    for(var i= 0; i < uploadResult.length; i++) {
        if (uploadResult[i] != null) {
            for(var j = 0; j < uploadResult[i].length; j++) {
                jsonObj =  uploadResult[i][j];
                if (i + j > 0) {
                    jsonStr = jsonStr + ",";
                }
                jsonStr = jsonStr + JSON.stringify(jsonObj);
            }
        }
    }
    jsonStr = jsonStr + ']';
    var obj = getForm();
    var tmp = obj.delete_files.value;
    if (tmp != null && tmp != "") {
        obj.delete_files.value = encodeURI('[' + tmp + ']');
    }

    obj.attachments_info.value = encodeURI(jsonStr);

    obj.submit();
}

function fudReady(){

    $(document).bind('drop dragover', function (e) {
        e.preventDefault();
    });

    $(document).bind('dragover', function (e) {
        var dropZone = $('#dropzone'),
            timeout = window.dropZoneTimeout;
        if (!timeout) {
            dropZone.addClass('in');
        } else {
            clearTimeout(timeout);
        }
        var found = false,
            node = e.target;
        do {
            if (node === dropZone[0]) {
                found = true;
                break;
            }
            node = node.parentNode;
        } while (node != null);
        if (found) {
            dropZone.addClass('hover');
        } else {
            dropZone.removeClass('hover');
        }
        window.dropZoneTimeout = setTimeout(function () {
            window.dropZoneTimeout = null;
            dropZone.removeClass('in hover');
        }, 100);
    });
}

function getCurrentSumSize() {
    var sumsizeElm = $("#sumsize");
    var size = 0;
    if (sumsizeElm.length > 0) {
        var text = sumsizeElm.text();
        //alert(text);
        if (text.indexOf('총:') >= 0 && text.indexOf('/') > 0) {
            //alert(text.substring(text.indexOf('총:') + 2, text.indexOf('/')).trim());
            size += getSizeReal(text.substring(text.indexOf('총:') + 2, text.indexOf('/')).trim());
        }
    }
    return size;
}

function initJqueryUpload() {
    // Detecting IE
    if (isIE () && isIE () <= 9) {
        oldIE = true;
        DROP_FILE = "";
    }

    // Initialize the jQuery File Upload widget:
    if (ju_maxsize > 0) {
        sizelimit = true;
    }

    updateSumSize('', false);
    $('#fileupload').fileupload({
        url: uploadURL,
        dropZone: $('#dropzone'),
        autoUpload: false,
        dataType: 'json',
        async: true,
        add: function (e, data) {
            console.log('add');
            var currentfiles = [];
            var currentsize = getCurrentSumSize();
            $(this).fileupload('option').filesContainer.children().each(function(){
                if ($('.name', this) != null && $('.name', this).length > 0) {
                    currentfiles.push($.trim($('.name', this).text()));
                }
            });

            data.files = $.map(data.files, function(file,i){
                var tmpVal = file.name.toLowerCase();
                ext = tmpVal.substring(tmpVal.lastIndexOf('.')+1, tmpVal.length);
                if ($.inArray(ext, fileGbn) < 0) {
                    alert(ext + " 는(은) 업로드 할 수 없는 확장자 입니다.");
                    return null;
                } else if ($.inArray(file.name,currentfiles) >= 0) {
                    alert(file.name + " 는(은) 이미 추가된 파일입니다.");
                    return null;
                }

                if (file.size < 0) {
                    alert("사이즈 정보가 인식 안 됩니다.");
                    return null;
                }

                if (file.size > maxsize) {
                    alert("제한 사이즈를 초과되었습니다.");
                    return null;
                }
                if (sizelimit && file.size + currentsize > ju_maxsize) {
                    alert("제한 사이즈를 초과되었습니다.");
                    return null;
                }
                updateSumSize(file.size, true);
                return file;
            });

            if (data.files.length > 0) {
                if ($("#dropzone_holder") != null) {
                    $("#dropzone_holder").remove();
                }
                $("#new_file_add").val(1);

                $("#boardsubmit").on('click', function () {
                    if(getForm().checkValidity() && data.context.is(":visible")) {
                       data.submit();
                   }
                });
            }

            var that = this;
            $.blueimp.fileupload.prototype.options.add.call(that, e, data);
        },
        done: function (e, data) {
            $("#boardsubmit").off('click');
            $.each(data.result, function (index, file) {
                uploadResult[fileUploadCount++] = data.result;
            });
        },
        stop: function (e) {
            var that = this;
            $.blueimp.fileupload.prototype.options.stop.call(that, e);
            submitForm();
        }
    });
}