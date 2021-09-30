enum class Coffee(val water: Int, val milk: Int, val beans: Int, val cups: Int, val money: Int) {
    ESPRESSO(250,0,16,1,4),
    LATTE(350,75,20,1,7),
    CAPPUCCINO(200,100,12,1,6)
}

enum class State {
    ACTION, BUY, FILL, TAKE, REMAINING, EXIT
}

class CoffeeMachine(
    var water: Int,
    var milk: Int,
    var beans: Int,
    var cups: Int,
    var money: Int
) {
    var state = State.ACTION
    
    fun process() {
        when(state) {
            State.ACTION -> action()
            State.BUY -> buy()
            State.FILL -> fill()
            State.TAKE -> take()
            State.REMAINING -> showstatus()
            else -> State.EXIT
        }
    }
    
    fun action(): Unit {
        print("Write action (buy, fill, take, remaining, exit):")
        var accion = readLine()!!
        state = when(accion) {
            "buy" -> State.BUY
            "fill" -> State.FILL
            "take" -> State.TAKE
            "remaining" -> State.REMAINING
            else -> State.EXIT
        }
    }
    
    fun doNothing() {}
    
    fun buy(): Unit {
        print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ")
        val orden = readLine()!!
        for (type in Coffee.values()) {
            if (orden == (type.ordinal + 1).toString()) {
                if (enoughsupplies(type)) {
                    water -= type.water
                    milk -= type.milk
                    beans -= type.beans
                    cups -= type.cups
                    money += type.money
                }
            } else doNothing()
        }
        state = State.ACTION
    }
    
    fun enoughsupplies(drink: Coffee): Boolean {
        var bool = false
        if (water < drink.water || milk < drink.milk || beans < drink.beans || cups < drink.cups) {
            if (water < drink.water) {
                println("Sorry, not enough water!")
            } else if (milk < drink.milk) {
                println("Sorry, not enough milk!") 
            } else if(beans < drink.beans) {
                println("Sorry, not enough beans!") 
            } else if(cups < drink.cups) {
                println("Sorry, not enough cups!")}
        } else {
            bool = true
            println("I have enough resources, making you a coffee!")
            }
        return bool
    }
    
    fun fill(): Unit {
        print("Write how many ml of water do you want to add: ")
        val watersupply = readLine()!!.toInt()
        print("Write how many ml of milk do you want to add: ")
        val milksupply = readLine()!!.toInt()
        print("Write how many grams of coffee beans do you want to add: ")
        val beanssupply = readLine()!!.toInt()
        print("Write how many disposable cups of coffee do you want to add: ")
        val cupssupply = readLine()!!.toInt()
        water += watersupply
        milk += milksupply
        beans += beanssupply
        cups += cupssupply
        state = State.ACTION
    }
    
    fun showstatus(): Unit {
        println("The coffee machine has:")
        println("$water of water")
        println("$milk of milk")
        println("$beans of coffee beans")
        println("$cups of disposable cups")
        println("$money of money")
        state = State.ACTION
    }

    fun take(): Unit {
        println("I gave you $$money")
        money = 0
        state = State.ACTION
    }
}

fun main() {
    val cafetera = CoffeeMachine(400, 540, 120, 9, 550)
    do {      
        cafetera.process()
    } while (cafetera.state != State.EXIT)
}