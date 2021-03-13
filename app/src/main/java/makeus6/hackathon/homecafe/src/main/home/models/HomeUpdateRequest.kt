package makeus6.hackathon.homecafe.src.main.home.models

data class HomeUpdateRequest (
        val boardId:Int,
        val description:String,
        val pictures:ArrayList<String>
)