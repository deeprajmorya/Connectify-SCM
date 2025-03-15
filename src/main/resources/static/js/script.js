console.log("Script Loaded");


// change theme work start 
let currentTheme = getTheme();

document.addEventListener('DOMContentLoaded',()=>{
    changeTheme(currentTheme);
})

function changeTheme(currentTheme){
    //set to web page 
    document.querySelector("html").classList.add(currentTheme);

    //set the listner to change theme button
    const changeThemeBtn = document.querySelector("#theme_change_btn");

    
    changeThemeBtn.addEventListener("click",(event) => {
        console.log("chalra h");
        let oldTheme = currentTheme;

        if(currentTheme=="dark"){
            currentTheme="light";
        }else{
            currentTheme="dark";
        }

        // updating in the local storage 
        setTheme(currentTheme);

        // removing the old theme
        document.querySelector('html').classList.remove(oldTheme);
        // adding new current theme  
        document.querySelector('html').classList.add(currentTheme);

        //changing the button text
        changeThemeBtn.querySelector("span").textContent= 
        currentTheme == "light" ? "Dark" : "Light";
    }); 
}

//set theme to local storage 
function setTheme(theme){
    localStorage.setItem("theme",theme);
}

//get theme from local storage 
function getTheme(){
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

// change theme work end