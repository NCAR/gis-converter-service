<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> 

<html xmlns="http://www.w3.org/1999/xhtml" >

<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>File Request Summary</title>

</head>

<body>

	<h2>Annual Mean Product Request Details:</h2>
	
	Resolution: ${conversionRequest.parameters.scale}<br>
	Variable: ${conversionRequest.parameters.variable}<br>
	Scenario: ${conversionRequest.parameters.scenario}<br>
	Start Year: ${conversionRequest.parameters.startYear}<br>
	End Year: ${conversionRequest.parameters.endYear}<br>
	
	
	<br>
	Requested File: ${conversionRequest.dataFileName} <br>
	File Exists: <b>${dataFileExists}</b><br>



</body>