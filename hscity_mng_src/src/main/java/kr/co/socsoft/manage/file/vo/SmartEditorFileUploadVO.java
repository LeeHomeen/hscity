package kr.co.socsoft.manage.file.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.multipart.MultipartFile;

public class SmartEditorFileUploadVO {
    private MultipartFile Filedata;
    private String callback;
    private String callback_func;

    public MultipartFile getFiledata() {
        return Filedata;
    }

    public void setFiledata(MultipartFile filedata) {
        Filedata = filedata;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getCallback_func() {
        return callback_func;
    }

    public void setCallback_func(String callback_func) {
        this.callback_func = callback_func;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
