import org.w3c.dom.*;
//import org.apache.xerces.parsers.DOMParser;
public class Searcher
{
public static void main(String args[])
{
String st1="e:\\xml\\customers" +
		".xml";
	String st2="bhjkgk";
FindElements findElements = new FindElements(st1, st2);
}
}
class FindElements
{
static String displayStrings[] = new String[1000];
static int numberDisplayLines = 0;
static String searchFor;
public FindElements (String uri, String searchString)
{
searchFor = searchString;
displayDocument(uri);
for(int loopIndex = 0; loopIndex < numberDisplayLines; loopIndex++){
System.out.println(displayStrings[loopIndex]);
}
}
public static void displayDocument(String uri)
{
try {
com.sun.org.apache.xerces.internal.impl.xs.dom.DOMParser parser = new com.sun.org.apache.xerces.internal.impl.xs.dom.DOMParser();
parser.parse(uri);
Document document = parser.getDocument();
NodeList nodeList =document.getElementsByTagName(searchFor);
if (nodeList != null) {
	System.out.println("Node found");
for (int loopIndex = 0; loopIndex < nodeList.getLength();
loopIndex++ ) {
display(nodeList.item(loopIndex), "");
}
}

	

} catch (Exception e) {
e.printStackTrace(System.err);
}
}
public static void display(Node node, String indent)
{
if (node == null) {
	System.out.println("Node not found");
return;
}
int type = node.getNodeType();
switch (type) {
case Node.DOCUMENT_NODE: {
displayStrings[numberDisplayLines] = indent;
displayStrings[numberDisplayLines] +=
"<?xml version=\"1.0\" encoding=\""+
"UTF-8" + "\"?>";
numberDisplayLines++;
display(((Document)node).getDocumentElement(), "");
break;
}
case Node.ELEMENT_NODE: {
displayStrings[numberDisplayLines] = indent;
displayStrings[numberDisplayLines] += "<";
displayStrings[numberDisplayLines] += node.getNodeName();
int length = (node.getAttributes() != null) ?
node.getAttributes().getLength() : 0;
Attr attrs[] = new Attr[length];
for (int loopIndex = 0; loopIndex < length; loopIndex++) {
attrs[loopIndex] =
(Attr)node.getAttributes().item(loopIndex);
}
for (int loopIndex = 0; loopIndex < attrs.length;
loopIndex++) {
Attr attr = attrs[loopIndex];
displayStrings[numberDisplayLines] += " ";
displayStrings[numberDisplayLines] += attr.getNodeName();
displayStrings[numberDisplayLines] += "=\"";
displayStrings[numberDisplayLines] +=
attr.getNodeValue();
displayStrings[numberDisplayLines] += "\"";
}
displayStrings[numberDisplayLines] += ">";
numberDisplayLines++;
NodeList childNodes = node.getChildNodes();
if (childNodes != null) {
length = childNodes.getLength();
indent += " ";
for (int loopIndex = 0; loopIndex < length; loopIndex++ ) {
display(childNodes.item(loopIndex), indent);
}
}
break;
}
case Node.CDATA_SECTION_NODE: {
displayStrings[numberDisplayLines] = indent;
displayStrings[numberDisplayLines] += "<![CDATA[";
displayStrings[numberDisplayLines] += node.getNodeValue();
displayStrings[numberDisplayLines] += "]]>";
numberDisplayLines++;
break;
}
case Node.TEXT_NODE: {
displayStrings[numberDisplayLines] = indent;
String newText = node.getNodeValue().trim();
if(newText.indexOf("\n") < 0 && newText.length() > 0) {
displayStrings[numberDisplayLines] += newText;
numberDisplayLines++;
}
break;
}
case Node.PROCESSING_INSTRUCTION_NODE: {
displayStrings[numberDisplayLines] = indent;
displayStrings[numberDisplayLines] += "<?";
displayStrings[numberDisplayLines] += node.getNodeName();
String text = node.getNodeValue();
if (text != null && text.length() > 0) {
displayStrings[numberDisplayLines] += text;
}
displayStrings[numberDisplayLines] += "?>";
numberDisplayLines++;
break;
}
}
if (type == Node.ELEMENT_NODE) {
displayStrings[numberDisplayLines] = indent.substring(0,
indent.length() - 4);
displayStrings[numberDisplayLines] += "</";
displayStrings[numberDisplayLines] += node.getNodeName();
displayStrings[numberDisplayLines] += ">";
numberDisplayLines++;
indent+= " ";
}
}
}