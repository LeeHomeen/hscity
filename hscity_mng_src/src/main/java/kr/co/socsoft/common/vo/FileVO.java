package kr.co.socsoft.common.vo;

public class FileVO {
    private Long fileSize;
    private String fileName;
    private byte[] fileData;

    public FileVO(String fileName, Long fileSize, byte[] fileData) {
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
