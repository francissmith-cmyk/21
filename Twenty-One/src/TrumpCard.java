public class TrumpCard {
    private String function;
	private String explanation;
	private String color;

    public TrumpCard(String f, String e, String d) 
    {
        this.function = f;
        this.explanation = e;
        this.color = d;
    }

    public String getFunction() 
    {
        return this.function;
    }

    public void setFunction(String function) 
    {
        this.function = function;
    }
    public String getExplanation() 
    {
        return this.explanation;
    }

    public void setExplanation(String explanation) 
    {
        this.explanation = explanation;
    }
    
    public String getColor() 
    {
        return this.color;
    }

    public void setColor(String function) 
    {
        this.color = function;
    }
}