<html>
<head>
<style> 
.div1 {
margin-top: 25px;
margin-left: 20%;
    margin-right: 25%;
  width: 700px;
  height: 550px;
  border: 2px solid black;
}

.button {
width: 200px;
  height: 45px;
margin-left: 30px%;
margin-right: 25%;
  background-color: #000000;
   border: 1.5px solid black;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}
.button1 {
width: 200px;
  height: 45px;
margin-left: 30px%;
margin-right: 25%;
  background-color: #FFFFFF;
  border: 1.5px solid black;
  color: black;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}

</style>
</head>
<body>
<div class="div1" >

<table style="  margin-top: 30px;  font-size: 15px;   margin-left: 14%;">

<tr><th>
<td ><b>Discipline and Role</b></td></th></tr>
<tr><th>
<td><img style="margin-left: 7px%; height:14px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${discipline}, ${role}</td>
</th></tr>
<tr><th>
</tr></th>
<tr><th>
<td><b>Contractor</b></td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${contractorEmployee}-'${contractor}'</td>
</th></tr>
<tr><th>
</tr></th>

<tr><th>
<td><b>Supplier Type</b></td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${supplierType}</td>
</th></tr>
<tr><th>
</tr></th>
<tr><th>
<td><b>Start and End Date</b></td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${startDate} - ${endDate}</td>
</th></tr>
<tr><th>
</tr></th>
<tr><th>
<td><b>Assignment Work Locations</b></td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${workLocations}</td>
</th></tr>
<tr><th>
</tr></th>
<tr><th>
<td><b>Reason for recruiting</b></td></th></tr>
<tr><th>
<td><img style="height:15px;width:15px"src="https://img.icons8.com/wired/64/000000/edit-property.png"/> ${reasonForRecruiting}</td>
</th></tr>
<tr><th>
</tr></th>
<tr><th>
</tr></th>
<tr><th>
<td><b>Milestones</b></td></th></tr>
</table>

<table style=" border: 1px solid black;width: 75%;  margin-left: 15%; font-size: 12px;">
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
<tr><th>
</tr></th>
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
 <div ><center>Total Cost            ${totalCost}</center></div>
</div>
</div>
<br>

<center><button class="button1"><b>DECLINE</b></button>
<input type="button" class="button" value="APPROVE"></center>
</body>
</html>