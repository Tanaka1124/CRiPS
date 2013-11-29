package ClassBlockFileModel;

import java.io.PrintStream;

public class PublicMethodCommandWriter extends BasicModel {

	private PublicMethodInfo method;

	public void setMethods(PublicMethodInfo method) {
		this.method = method;
	}

	public void printCommands(PrintStream out) {
		int lineNum = 0;
		makeIndent(out, lineNum);
		out.println("<BlockGenus name=\"" + method.getName()
				+ "\" kind=\"command\" initlabel=\"" + method.getName()
				+ "\" color=\"255 0 0\">");

		makeIndent(out, ++lineNum);
		if (method.getParameters() != null) {
			out.println("<BlockConnectors>");

			++lineNum;
			for (String parameter : method.getParameters()) {
				// TODO connector-typeを引数の形で変える　parameterは int
				// xxのような型＋変数名の形で保持されていることに注意されたし
				String parameterType = parameter.substring(0,
						parameter.indexOf(" "));
				String parameterName = parameter.substring(
						parameterType.length(), parameter.length());
				makeIndent(out, lineNum);
				out.println("<BlockConnector label=\"" + parameterName
						+ "\" connector-kind=\"socket\" connector-type=\""
						+ parameterType + "\">");

				makeIndent(out, lineNum);
				out.println("</BlockConnector>");
				// 引数の設定
			}
			makeIndent(out, --lineNum);
			out.println("</BlockConnectors>");
		}

		makeIndent(out, lineNum);
		out.println("<LangSpecProperties>");
		makeIndent(out, ++lineNum);
		out.println("<LangSpecProperty key=\"vm-cmd-name\" value=\""
				+ method.getName() + "\"></LangSpecProperty>");
		makeIndent(out, lineNum);
		out.println("<LangSpecProperty key=\"stack-type\" value=\"breed\"></LangSpecProperty>");

		makeIndent(out, --lineNum);
		out.println("</LangSpecProperties>");

		makeIndent(out, --lineNum);
		out.println("</BlockGenus>");
		out.println();

	}
}
