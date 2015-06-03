window.onload = init;

function init() {
	getHistoric();
}

//
// With XMLHttpRequest Level 2 (implemented in new versions of Firefox, Safari
// and Chrome) you can check progress and check for the "load" event with the
// onload event handler instead of checking the onreadystatechange
//
function getLatest() {
	// change the URL to match the location where you
	// put the sales.json file
	var url = "http://localhost:9000/GeyserWebappV2/latest.xml";
	var request = new XMLHttpRequest();	
	request.open("GET", url);
	//request.setRequestHeader("Authorization", "Basic " + btoa("admin:admin"));
	request.onload = function() {
		if (request.status == 200) {
			updateLatest(request.responseText);
		}
	};
	request.send(null);
}

function updateLatest(responseText) {
	var salesDiv = document.getElementById("latest");
	
	var xmlDoc = $.parseXML( responseText );
		var $xml = $( xmlDoc );
		var $title = $xml.find( "content" );

	var content = JSON.parse(atob($title.text()));
	console.log('Element state: ' + content.e);
	
	var div = document.createElement("div");
	div.setAttribute("class", "saleItem");
	div.innerHTML = "Element state: " + content.e;
	salesDiv.appendChild(div);
}

function getHistoric() {
	// change the URL to match the location where you
	// put the sales.json file
	var url = "http://localhost:9000/GeyserWebappV2/gcd_2015-01-19.csv";
	var request = new XMLHttpRequest();	
	request.open("GET", url);
	//request.setRequestHeader("Authorization", "Basic " + btoa("admin:admin"));
	request.onload = function() {
		if (request.status == 200) {
			updateHistoric(request.responseText);
		}
	};
	request.send(null);
}

function updateHistoric(responseText) {
	g = new Dygraph(document.getElementById("historic"),
			responseText,
            { 
		labels: [ "Time", "Far", "Inside", "Near", "Solar", "Ambient"], 
    	rollPeriod: 10,
    	showRoller: true,
    	title: '29 March 2015'
	
            });
}



