import java.awt.Color;

public class Theme {
	public String name;
	public Color headingBG;
	public Color headingText;
	public Color contentBG;
	public Color contentText;
	public Color alternatingBG;
	public Color alternatingText;
	public Color outline;
	public Color generalBG;
	
	public Theme (String n, Color hBG, Color hText, Color cBG, Color cText, Color aBG, Color aText, Color outline, Color generalBG) {
		this.name = n;
		this.headingBG = hBG;
		this.headingText = hText;
		this.contentBG = cBG;
		this.contentText = cText;
		this.alternatingBG = aBG;
		this.alternatingText = aText;
		this.outline = outline;
		this.generalBG = generalBG;
	}

	public String getName() {
		return this.name;
	}
		
	public String getEntryNotation() {
		//"Theme Name",(heading bg),(heading text color),(content bg),(content color),(alternating bg),(alternating text color),(outline color),(general background)
		String headingRGB = "("+ this.headingBG.getRed() + "," + this.headingBG.getGreen() + "," + this.headingBG.getBlue() + ")";
		String headingTextRGB = "("+ this.headingText.getRed() + "," + this.headingText.getGreen() + "," + this.headingText.getBlue() + ")";
		String contentRGB = "("+ this.contentBG.getRed() + "," + this.contentBG.getGreen() + "," + this.contentBG.getBlue() + ")";
		String contentTextRGB = "("+ this.contentText.getRed() + "," + this.contentText.getGreen() + "," + this.contentText.getBlue() + ")";
		String alternatingRGB = "("+ this.alternatingBG.getRed() + "," + this.alternatingBG.getGreen() + "," + this.alternatingBG.getBlue() + ")";
		String alternatingTextRGB = "("+ this.alternatingText.getRed() + "," + this.alternatingText.getGreen() + "," + this.alternatingText.getBlue() + ")";
		String outlineRGB = "("+ this.outline.getRed() + "," + this.outline.getGreen() + "," + this.outline.getBlue() + ")";
		String generalRGB = "("+ this.generalBG.getRed() + "," + this.generalBG.getGreen() + "," + this.generalBG.getBlue() + ")";
		String fullNotation = "\"" + this.name + "\"" + "-" + headingRGB + "-" + headingTextRGB + "-" + contentRGB + "-" + contentTextRGB + "-" + alternatingRGB + "-" + alternatingTextRGB + "-" + outlineRGB + "-" + generalRGB;
		return fullNotation;
	}
	
}
