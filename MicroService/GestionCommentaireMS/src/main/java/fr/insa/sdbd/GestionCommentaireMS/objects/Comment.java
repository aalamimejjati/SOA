package fr.insa.sdbd.GestionCommentaireMS.objects;

public class Comment {
    private int mission , requester , id;
		private String comment;
    public Comment(){}

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public int getMission() {
        return mission;
    }

    public int getRequester() {
        return requester;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setMission(int mission) {
        this.mission = mission;
    }

    public void setRequester(int requester) {
        this.requester = requester;
    }
}
