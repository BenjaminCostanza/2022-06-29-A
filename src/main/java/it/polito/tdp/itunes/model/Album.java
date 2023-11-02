package it.polito.tdp.itunes.model;

public class Album implements Comparable<Album>{
	private Integer albumId;
	private String title;
	private int nCanzoni;
	private int bilancio;
	
	public Album(Integer albumId, String title) {
		super();
		this.albumId = albumId;
		this.title = title;
	}
	
	public Album(Integer albumId, String title, int nCanzoni) {
		super();
		this.albumId = albumId;
		this.title = title;
		this.nCanzoni = nCanzoni;
		this.bilancio = 0;
	}


	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getnCanzoni() {
		return nCanzoni;
	}

	public void setnCanzoni(int nCanzoni) {
		this.nCanzoni = nCanzoni;
	}

	
	public void increaseBilancio(int n) {
		this.bilancio = this.bilancio + n;
	}
	
	public void decreaseBilancio(int n) {
		this.bilancio = this.bilancio - n;
	}
	
	public int getBilancio() {
		return bilancio;
	}

	public void setBilancio(int bilancio) {
		this.bilancio = bilancio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (albumId == null) {
			if (other.albumId != null)
				return false;
		} else if (!albumId.equals(other.albumId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public int compareTo(Album o) {
		if(o.bilancio - this.bilancio==0) {
			return -(this.title.compareTo(title));
		}
		// TODO Auto-generated method stub
		return o.bilancio - this.bilancio;
	}
	
	
	
}
