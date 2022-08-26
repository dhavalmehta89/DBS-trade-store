package model;

import java.time.LocalDate;
import java.util.Objects;

public class Trade {
    private String tradeId;

    private Integer version;

    private String counterPartyId;

    private String bookId;

    private LocalDate maturityDate;

    private LocalDate createdDate;

    private Boolean expired;

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + tradeId + '\'' +
                ", version=" + version +
                ", counterPartyId='" + counterPartyId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return tradeId.equals(trade.tradeId) && version.equals(trade.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, version);
    }

    public Trade(String tradeId, Integer version) {
      this(tradeId,version,null,null,null,null,null);
    }

    public Trade(String tradeId, Integer version, String counterPartyId, String bookId, LocalDate maturityDate, LocalDate createdDate, Boolean expired) {
        if(tradeId==null || tradeId.isEmpty() || version == null || version.equals(0)){
            throw new IllegalStateException("TradeId and Version must not be null or empty");
        }
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

  
}
