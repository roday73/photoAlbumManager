package com.rpo.domain;

public class AlbumDTO{
	
	private Long id;
    private String title;
    private Long userId;

    public AlbumDTO(){}
    
    public AlbumDTO(Long id, String title, Long userId) {
		super();
		this.id = id;
		this.title = title;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Long getUserId() {
		return userId;
	}

	@Override
    public String toString(){
        return "Album-id:"+this.id+", title:"+this.title+", userId:"+this.userId;
    }

}
