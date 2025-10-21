package org.photomgr.dto;

import java.nio.file.attribute.FileTime;
import java.util.Date;

public class FileMetaDto {

    /**
     * .append("CreationTime: ").append( dFormat.format( attr.creationTime().toMillis() ) )
     * .append("(").append(attr.creationTime().toMillis()).append(") [").append(attr.creationTime()).append("]\n")
     * .append("lastAccessTime: ").append( dFormat.format( attr.lastAccessTime().toMillis() ) )
     * .append("(").append(attr.lastAccessTime().toMillis()).append(") [").append( attr.lastAccessTime() ).append("]\n")
     * .append("lastModifiedTime: ").append( dFormat.format( attr.lastModifiedTime().toMillis() ) )
     * .append("(").append(attr.lastModifiedTime().toMillis()).append(") [").append( attr.lastModifiedTime() ).append("]\n")
     * .append("isDirectory: ").append(attr.isDirectory()).append("\n")
     * .append("isOther: ").append(attr.isOther()).append("\n")
     * .append("isRegularFile: ").append(attr.isRegularFile()).append("\n")
     * .append("isSymbolicLink: ").append(attr.isSymbolicLink()).append("\n")
     * .append("size: ").append(attr.size()).append("\n");
     */
    private String fullPath;
    private String fileName;
    private String fileExt;
    private FileTime creationTime;
    private FileTime lastAccessTime;
    private FileTime lastModifiedTime;
    private Date fileDate;
    private Long size;

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public FileTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(FileTime creationTime) {
        this.creationTime = creationTime;
    }

    public FileTime getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(FileTime lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public FileTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(FileTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public FileMetaDto() {
    }

    public FileMetaDto(String fullPath) {
        this.fullPath = fullPath;
    }

    @Override
    public String toString() {
        return "{" + // "FileMetaDto{"+
               "fullPath="+this.fullPath+
                ", fileName="+this.fileName+
                ", fileExt="+this.fileExt+
                ", creationTime="+this.creationTime+
                ", lastAccessTime="+this.lastAccessTime+
                ", lastModifiedTime="+this.lastModifiedTime+
                ", fileDate="+this.fileDate+
                ", size="+this.size
                + "}";
    }

    public long diffWithDate(Date otherDate){
        long diffInMillis = otherDate!=null? this.fileDate.getTime() - (otherDate.getTime()):0;
        return diffInMillis / (60 * 1000);
    }
}
