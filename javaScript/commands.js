const ifCondition = (cond) => { 
    let condArray = cond.split(" ")
    let thingNo = parseInt(condArray[0].slice(1))
    if (cond[0].toUpperCase() === "V") {
        let compareNo = parseInt(condArray[2])
        if (condArray[1] === ">") { return currentState.vars[thingNo] > compareNo }
        else if (condArray[1][0]=== "=") { return currentState.vars[thingNo] === compareNo }
        else if (condArray[1] === "<") { return currentState.vars[thingNo] < compareNo }
    }

    if (cond[0].toUpperCase() === "I") { 
        if (condArray[1] === "Owned") { return currentState.inventory.indexOf(thingNo) != -1 }
        else if (condArray[1] === "Unowned") { return currentState.inventory.indexOf(thingNo) == -1 }
    }
}

const junction = (currentData) => { 

    let juncs = []

    if (!Array.isArray(currentData[0])){ 
        juncs[0] = currentData
    }
    else {
        juncs = structuredClone(currentData)
    }


    for (let junc of juncs) { 
        if(ifCondition(junc[0])) { 
            newLabel(junc[1])
            return 0
        }
        if (junc[2]) { 
            newLabel(junc[2]) 
            return 0
        }
    }
 }


const commandHandler = {
    "Give Item": (commands) => { 
            currentState.inventory.push(commands[1]) 
    },

    "Remove Item": (commands) => { 
        let pl = currentState.inventory.indexOf(commands[1])
        if(pl !== -1) { currentState.inventory.splice(pl, 1) }
    },

    "Checkpoint": () => { 
        checkpointState = structuredClone(currentState)
    },

    "Game Over": () => { 
        currentButtons = ["Return to Checkpoint"]
        currentLabels = [checkpointState.label]
        currentState = {...checkpointState}
    },

    "Variable Math": (commands) => {
        let vMath = commands[1].split(" ")
        vMath[0] = vMath[0].slice(1)
        if (!currentState.vars[vMath[0]]) { currentState.vars[vMath[0]] = 0 }
        if(vMath[1] === "+") { currentState.vars[vMath[0]] += parseInt(vMath[2]) }
        if(vMath[1] === "-") { currentState.vars[vMath[0]] -= parseInt(vMath[2])  }
        if(vMath[1][0] === "=") { currentState.vars[vMath[0]] = parseInt(vMath[2]) }
    },

    "Play Music": (commands) => { 
        if (currentState.music != commands[1]) {
            currentState.music = commands[1]
            music.src = "./sound/" + commands[1]
            music.play()
        }
    },

    "Stop Music": () => { 
        music.pause()
    },

    "Play SFX": (commands) => { 
            sfx.src = "./sound/" + commands[1]
            sfx.play()
    },

    "Add Label If": (commands) => {  
        if(ifCondition(commands[1])) { 
            currentButtons.push(commands[2])
            currentLabels.push(commands[3])
         }
    } 



}


const runCommands = (commands) => {

    const handler = commandHandler[commands[0]]
    if (handler) { handler(commands) }
    else { 
        console.warn("Unknown command:", commands[0])
    }
}


