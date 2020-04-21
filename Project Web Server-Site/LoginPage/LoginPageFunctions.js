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

function checkLogin()
{
    window.localStorage.setItem('card', document.getElementById("cardcode").value);
    hideShowupElement("cardForm");
    hideShowupElement("passwordForm");
    window.localStorage.setItem('password', document.getElementById("password").value);
}

function hideShowupElement(id)
{
    var element = document.getElementById(id);
    if (element.style.display == "none") 
    {
        element.style.display = "block";
    } 
    else 
    {
      element.style.display = "none";
    }
}
