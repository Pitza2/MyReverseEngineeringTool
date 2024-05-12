# MyReverseEngineeringTool
## Requirements
-The tool accepts as command line arguments the name of a compiled file (*.jar file) and extracts all informations that are needed to describe the class diagram of the classes contained in this file. It is not necessary to graphicallly draw the class diagram, but to display (textually) all the informations of this type: classes, interfaces, methods, fields, relationships between classes/interfaces.

-The output extracted by your tool must be in a format accepted by a UML drawing tool, such as yuml. Implement a good design for your tool, such that new output formats for different UML drawing tools can be added with minimal effort. Your tool also must be able to receive additional configuration options:

give a set of classes to be ignored when drawing the diagram (for example, all java.lang.* classes), and whatever you want to ignore (-b {className1,className2} to ignore className1 and className2)
write class names as fully qualified names: yes/no (-n for fully qualified names)
show method names: yes/no (-m arg to not show)
show attributes: yes/no (-a arg to not show)

The tool can distinguish 4 types of relationships between classes and/or interfaces: extends, implements, association, dependency. Aggregation and composition relationships are reverse engineeered as associations by this tool.
Cardinality of association relationships is ignored.
