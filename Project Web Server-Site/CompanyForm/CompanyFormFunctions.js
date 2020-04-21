function populateNomosSelect()
{
    var list1 = document.getElementById("nomosList");
 
    list1.options[list1.options.length] = new Option("--Select--", "");
    list1.options[list1.options.length] = new Option("First", "1");
    list1.options[list1.options.length] = new Option("Second", "2");
}

function populateDimosSelect()
{
    var list1 = document.getElementById("dimosList");
 
    list1.options[list1.options.length] = new Option("--Select--", "");
    list1.options[list1.options.length] = new Option("First", "1");
    list1.options[list1.options.length] = new Option("Second", "2");
}

function populateCountrySelect()
{
    var list1 = document.getElementById("countryList");
 
    list1.options[list1.options.length] = new Option("--Select--", "");
    list1.options[list1.options.length] = new Option("First", "1");
    list1.options[list1.options.length] = new Option("Second", "2");
}

function populateCategorySelect()
{
    var list1 = document.getElementById("categoryList");
 
    list1.options[list1.options.length] = new Option("--Select--", "");
    list1.options[list1.options.length] = new Option("First", "1");
    list1.options[list1.options.length] = new Option("Second", "2");
}

function toggleTopNav()
{
    navSize = document.getElementById("leftSideBar").style.width;
    if (navSize > "0px") 
    {
        return closeNav();
    }
    return openNav();
}

function openNav() 
{
    document.getElementById("leftSideBar").style.width = "250px";
}
  
function closeNav() 
{
    document.getElementById("leftSideBar").style.width = "0";
}