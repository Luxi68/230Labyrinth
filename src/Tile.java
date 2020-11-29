import javafx.scene.image.Image;

public abstract class Tile {
	String tileType;
	Image image;
	
	protected Tile(String tileType)	{
		this.tileType = tileType;
	}
	
	public void setTileType (String tileType)	{
		this.tileType = tileType;
	}

	public void setImage (Image image) {
		this.image = image;
	}

	public String getTileType()	{
		return tileType;
	}

	public Image getImage() {
		return image;
	}
}
