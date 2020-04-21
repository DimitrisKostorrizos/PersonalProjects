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