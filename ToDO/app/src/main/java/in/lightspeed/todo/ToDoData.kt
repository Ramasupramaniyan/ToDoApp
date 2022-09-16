package `in`.lightspeed.todo

class ToDoData {
    lateinit var todo:String
    lateinit var date:String
    lateinit var startTime:String
    lateinit var endTime:String
    constructor(){}
    constructor(todo:String,date:String,startTime:String,endTime:String){
        this.todo = todo
        this.date = date
        this.startTime = startTime
        this.endTime = endTime
    }
}