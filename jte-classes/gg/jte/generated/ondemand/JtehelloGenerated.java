package gg.jte.generated.ondemand;
import org.group4.HelloPage;
public final class JtehelloGenerated {
	public static final String JTE_NAME = "hello.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,12,12,12,15,15,15,15,15,15,19,19,19,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, HelloPage page) {
		jteOutput.writeContent("\n<!doctype html>\n<html lang=\"en\" data-bs-theme=\"dark\">\n<head>\n    <meta charset=\"utf-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n    <title>Bootstrap demo</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">\n</head>\n<body>\n    ");
		gg.jte.generated.ondemand.JtenavbarGenerated.render(jteOutput, jteHtmlInterceptor);
		jteOutput.writeContent("\n    <div class=\"container\">\n        <h1>Homepage</h1>\n        <p>The <b>user of the day</b> is ");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(page.userName);
		jteOutput.writeContent(" (karma: ");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(page.userKarma);
		jteOutput.writeContent(")!</p>\n    </div>\n<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		HelloPage page = (HelloPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
