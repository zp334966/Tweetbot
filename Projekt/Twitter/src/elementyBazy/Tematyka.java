package elementyBazy;

public class Tematyka {
	private String tag;
	private short skutecznosc;
	
	public Tematyka(String tag, short skutecznosc) {
		this.tag = tag;
		this.skutecznosc = skutecznosc;
	}
	public String getTag() {
        return tag;
    }
	public short getSkutecznosc() {
        return skutecznosc;
    }
	public void setSkutecznosc(short s) {
        this.skutecznosc = s;
    }
	
	

}
