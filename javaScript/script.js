const getData = (label) => gameData["mainData"][label]

function changeButtons(buttonLabels, currentLabels) {
    for(let button of buttons){
        button.style.display = "none"
        button.onclick = null
    }

    if (!buttonLabels) { return 0 }

    for (let i = 0; i < buttonLabels.length; i++) {
      buttons[i].innerText = buttonLabels[i]
      buttons[i].style.display = "block"
      buttons[i].onclick = () => {
            newLabel(currentLabels[i])
        };
     }
}


const showMenu = () => { 

        if (currentLabels[0] === "Save") { 
            newLabel(currentState.label)
            return 0
        }
            const mainMenu = {
            "text": [inventoryText()],
            "options": ["Save", "Load", "Back"],
            "labels": ["Save", "Load", currentState.label],
        }

        prepareTextAndOptions(mainMenu)
        changeButtons(currentButtons, currentLabels)
}


const saveGame = () => {
    let states = {
        current: {...currentState},
        checkpoint: {...checkpointState}
    }
    localStorage.setItem("saveData", JSON.stringify(states))
    newLabel(currentState.label)
}

const loadGame = () => {
    const save = JSON.parse(localStorage.getItem("saveData"))
    currentState = structuredClone(save.current)
    checkpointState = structuredClone(save.checkpoint)
    picdiv.style.backgroundImage = "url('./images/"+ currentState.image + "')"
    music.src = "./sound/" + currentState.music
    music.play()
    newLabel(currentState.label)
}

const inventoryText = () => {

    if(!gameData.metadata.inventory) { return "" }

    if (currentState.inventory.length < 1) { return "Inventory Empty"}
    let ret = "Inventory: "
    for(let i = 0; i < currentState.inventory.length; i++){
        ret += fullInventory[currentState.inventory[i]] + " "
        if (i < currentState.inventory.length - 1 ) ret += " || "
    }
    return ret
}

const prepareTextAndOptions = (data) => {
    gameText.innerText = data.text.join("\n")
    if (data.options) { 
        currentButtons = [...data.options]
    }
    if (data.labels) { currentLabels = [...data.labels] }
}

const newLabel = (label) => {

    sfx.pause()

    if (label === "Save") {
        saveGame()
        return 0
    }

    if (label === "Load") {
        loadGame()
        return 0
    }


    let currentData = { ...getData(label) }

    if (currentData.Junction) { 
        junction(currentData.Junction)
        return 0
     }

    currentState.label = label

    prepareTextAndOptions(currentData)

     if(gameData.metadata.images) {
        if (currentData.image) { 
            picdiv.style.backgroundImage = "url('./images/"+ currentData.image + "')"
            currentState.image = currentData.image
        }
        else { 
            if(gameData.metadata.defaultImages) {
                picdiv.style.backgroundImage = "url('./images/"+ label + ".png')" 
                currentState.image = label + ".png"
            }
        }
     }

    if (currentData.commands) {  
        if (Array.isArray(currentData.commands[0])) {  
            for (let command of currentData.commands) {
                runCommands(command, label)
            }
        }
        else {
            runCommands(currentData.commands) 
        }
    }
    changeButtons(currentButtons, currentLabels)

}

const init = () => {
    document.title = gameData.metadata.title
    menuBarTitle.innerText = gameData.metadata.title
    fullInventory = gameData.mainData.inventory

    menuButton.onclick = () => { showMenu() }

    for(let i = 0; i < gameData.metadata.noOfVariables; i++){
        currentState.vars[i] = 0
    }

    newLabel("titleScreen")
}




//global variables
const menuBarTitle = document.querySelector("#titleLabel")
const gameText = document.querySelector("#textLabel")
const menuButton = document.querySelector("#menuButton") //change when make save and load
const buttons = Array.from(document.querySelectorAll(".optionButton"))
const picdiv = document.querySelector("#imagediv")
const music = document.querySelector("#music")
const sfx = document.querySelector("#audio")
let fullInventory
let currentButtons = []
let currentLabels = []


let currentState = {
    "label": "",
    "inventory": [],
    "vars": [],
    "music": "",
    "image": ""
}

let checkpointState = {
    "label": "",
    "inventory": [],
    "vars": [],
    "music": "",
    "image": ""
}



init()



