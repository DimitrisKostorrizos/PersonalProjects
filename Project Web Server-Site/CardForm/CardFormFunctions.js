function passwordEquals()
{
    var password = document.getElementById("psw").value;
    var repeatedPassword = document.getElementById("pswr").value;
    if(password != repeatedPassword)
    {
        var message = "The passwords are not the same.";
        document.getElementById("passwordtip").innerHTML = message;
        return true;
    }
    else
    {
        document.getElementById("passwordtip").innerHTML = "Same passwords.";
        return false;
    }
}