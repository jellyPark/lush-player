package com.lush.javaAggregator.modles;

//import javax.persistence.Transient;

public class Audio {

  private long id;

  /**
   * The name of the file that was uploaded.
   */
  private String name;

  /**
   * Brief description about the media file.
   */
  private String description;

  /**
   * Public security ID (CDN unique key).
   */
  private String public_id;

  /**
   * Type of attached file.
   */
//  @Transient
  private String resource_type;

  /**
   * The directory on the CDN the file is stored.
   */
  private String directory;

  /**
   * The file MIME type that was uploaded.
   */
  private String mime_type;

  /**
   * The file extension that was uploaded.
   */
  private String file_extension;

  /**
   * The size of the file that has been uploaded.
   */
  private int size;

  /**
   * The duration of the file that has been uploaded.
   */
  private double duration;

  /**
   * Is the media file available for use?
   */
  private boolean status;

  /**
   * The URL of the file on the CDN for reference.
   */
//  @Transient
  private String url;

  /**
   * Security enhanced URL.
   */
//  @Transient
  private String secure_url;

  /**
   * Create at date.
   */
  private String created_at;
  /**
   * Update at date.
   */
  private String updated_at;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPublic_id() {
    return public_id;
  }

  public void setPublic_id(String public_id) {
    this.public_id = public_id;
  }

  public String getResource_type() {
    return resource_type;
  }

  public void setResource_type(String resource_type) {
    this.resource_type = resource_type;
  }

  public String getDirectory() {
    return directory;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  public String getMime_type() {
    return mime_type;
  }

  public void setMime_type(String mime_type) {
    this.mime_type = mime_type;
  }

  public String getFile_extension() {
    return file_extension;
  }

  public void setFile_extension(String file_extension) {
    this.file_extension = file_extension;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public double getDuration() {
    return duration;
  }

  public void setDuration(double duration) {
    this.duration = duration;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSecure_url() {
    return secure_url;
  }

  public void setSecure_url(String secure_url) {
    this.secure_url = secure_url;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(String updated_at) {
    this.updated_at = updated_at;
  }
}
