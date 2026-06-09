package com.parent.springai.intellidetect.dto;

public class AccidentStatsDTO {
    private Long totalAccidents;
    private Long severeAccidents;
    private Long todayAccidents;

    public AccidentStatsDTO() {}

    public AccidentStatsDTO(Long totalAccidents, Long severeAccidents, Long todayAccidents) {
        this.totalAccidents = totalAccidents;
        this.severeAccidents = severeAccidents;
        this.todayAccidents = todayAccidents;
    }

    // Getters
    public Long getTotalAccidents() {
        return totalAccidents;
    }

    public Long getSevereAccidents() {
        return severeAccidents;
    }

    public Long getTodayAccidents() {
        return todayAccidents;
    }

    // Setters
    public void setTotalAccidents(Long totalAccidents) {
        this.totalAccidents = totalAccidents;
    }

    public void setSevereAccidents(Long severeAccidents) {
        this.severeAccidents = severeAccidents;
    }

    public void setTodayAccidents(Long todayAccidents) {
        this.todayAccidents = todayAccidents;
    }

    // toString方法（可选）
    @Override
    public String toString() {
        return "AccidentStatsDTO{" +
                "totalAccidents=" + totalAccidents +
                ", severeAccidents=" + severeAccidents +
                ", todayAccidents=" + todayAccidents +
                '}';
    }
}