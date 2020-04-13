<html>
<head>
<style> 

.div1 {
margin-left: 25%;
    margin-right: 25%;
  width: 600px;
  height: 50px;
  border: 2px solid black;
}


.div2 {
margin-left: 25%;
    margin-right: 25%;
  width: 500px;
  height: 450px;  
  padding: 50px;
  border: 1px solid black;
}


.boxed {
background-color: #C0C0C0;
  border: 1px solid black;

}

.wordart {
  font-family: Arial, sans-serif;
  font-size: 2em;
  font-weight: bold;
  position: relative;
  z-index: 1;
  display: inline-block;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  
}
  .flex-container {
        
        align-items: center; 
      
margin-right:25%;
        background-color: #A9A9A9;
  margin-left: 25%;
        
 
      }

</style>
</head>
<body>

<div class="div1" >

<div class="wordart rainbow">IMAGINATION</span>
</div></div>
<div class="div2">
<div class="boxed">
  

<h1 style="font-size: 10px;"><center>Summary Of Answers</center></h1>
<table style="    font-size: 10px;   margin-left: 15%;">

<tr><th>
<td >Discipline and Role</td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${discipline}, ${role}</td>
</th></tr>

<tr><th>
<td>Contractor</td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${contractorEmployee}-'${contractor}'</td>
</th></tr>

<tr><th>
<td>Supplier Type</td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${supplierType}</td>
</th></tr>

<tr><th>
<td>Start and End Date</td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${startDate} - ${endDate}</td>
</th></tr>

<tr><th>
<td>Assignment Work Locations</td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${workLocations}</td>
</th></tr>

<tr><th>
<td>Reason for recruiting</td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${reasonForRecruiting}</td>
</th></tr>

</table>

<table style=" border: 1px solid black;width: 75%;  margin-left: 16%; font-size: 10px;">
  <tr style="border: 1px solid black;
  text-align: left;
  padding: 8px;">
   <th bgcolor="#A9A9A9">#</th>
    <th bgcolor="#A9A9A9">Task</th>
    <th bgcolor="#A9A9A9">Delivery date</th>
    <th bgcolor="#A9A9A9">Day rate</th>
	<th bgcolor="#A9A9A9">Total days</th>
	<th bgcolor="#A9A9A9"> Total(£)
  </tr>
  <tr style="border: 1px solid black;"background-color: #dddddd;">
    <td>1</td>
    <td>${task}</td>
    <td>${deliveryDate}</td>
    <td>${dayRate}</td>
    <td>${totalDays}</td>
    <td>${total}<td>
  </tr>
</table>
<br>
 <div class="flex-container">
 <div >Total Cost            ${totalCost}</div>

</div>
<style>

.button {

  background-color: black;
  border: none;
  color: white;
  padding: 5px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px;
  margin: 6px 2px;
  cursor: pointer;
}
</style>

<center><button class="button">REVIEW CONTRACT</button></center>
<p style="font-size:12px;">  Please review and approve documents  <br> </br>Requested by:<br>
<b>${requestedBy}</b>  <br>
${mailAddress}</p>
</div>



</body>
</html>
