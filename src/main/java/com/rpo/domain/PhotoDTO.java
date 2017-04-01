package com.rpo.domain;

public class PhotoDTO {

	private Long albumId;
	private Long id;
    private String title;
    private String url;
    private String thumbnailUrl;
    
    public PhotoDTO(){}
    
    public PhotoDTO(Long albumId, Long id, String title, String url, String thumbnailUrl) {
		super();
		this.albumId = albumId;
		this.id = id;
		this.title = title;
		this.url = url;
		this.thumbnailUrl = thumbnailUrl;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	@Override
    public String toString(){
        return "Photo-id:"+this.id+", title:"+this.title+", album id: "+this.albumId+", url: "+this.url+" "+", thumbNailUrl: "+this.thumbnailUrl;
    }

}
