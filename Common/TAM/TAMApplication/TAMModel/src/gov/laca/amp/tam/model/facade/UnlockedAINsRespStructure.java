package gov.laca.amp.tam.model.facade;

import gov.laca.amp.tam.model.pojo.TAMAssignmentPojo;

import java.io.Serializable;

import java.util.List;

public class UnlockedAINsRespStructure implements Serializable{
    private static final long serialVersionUID = 1L;

    public UnlockedAINsRespStructure() {
        super();
    }
  private   List<TAMAssignmentPojo> soaAinList;
  private Integer startRange;
    private Integer endRange;
  private Integer totalAssignedCount;  

  

    public void setStartRange(Integer startRange) {
        this.startRange = startRange;
    }

    public Integer getStartRange() {
        return startRange;
    }

    public void setEndRange(Integer endRange) {
        this.endRange = endRange;
    }

    public Integer getEndRange() {
        return endRange;
    }

    public void setSoaAinList(List<TAMAssignmentPojo> soaAinList) {
        this.soaAinList = soaAinList;
    }

    public List<TAMAssignmentPojo> getSoaAinList() {
        return soaAinList;
    }

    public void setTotalAssignedCount(Integer totalAssignedCount) {
        this.totalAssignedCount = totalAssignedCount;
    }

    public Integer getTotalAssignedCount() {
        return totalAssignedCount;
    }
}
