public class TrumpCard {
    private String function;
	private String explanation;

    public TrumpCard(String f, String e) 
    {
        this.function = f;
        this.explanation = e;
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
}