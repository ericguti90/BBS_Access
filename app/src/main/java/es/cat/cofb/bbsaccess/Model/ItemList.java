package es.cat.cofb.bbsaccess.Model;

public class ItemList
{
	private String title;
	private String date;

	public ItemList(String title, String date){
		this.title = title;
		this.date = date;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDate(){
		return date;
	}

}
