package com.bluemobi.pro.pojo;

/**
 * 配置POJO
 * @author yesong
 *
 */
public class Config {

	private Integer id;
	
	private Integer coin;
	
	private Integer coefficient;
	
	private Integer coinRegister;
	
	private Integer coinPerson;
	
	private Integer coinCard;
	
	private Integer coinUs;
	
	private Integer coinRec;
	
	private Integer integralRelease;
	
	/** 接单人 获得的积分  */
	private Integer integralAccept;
	
	/**  发起人获得的积分 */
	private Integer integralReleaseDeduct;
	
	/** 接单人 获得的积分  */
	private Integer integralAcceptDeduct;

	public Integer getIntegralRelease() {
		return integralRelease;
	}

	public void setIntegralRelease(Integer integralRelease) {
		this.integralRelease = integralRelease;
	}

	public Integer getIntegralAccept() {
		return integralAccept;
	}

	public void setIntegralAccept(Integer integralAccept) {
		this.integralAccept = integralAccept;
	}

	public Integer getIntegralReleaseDeduct() {
		return integralReleaseDeduct;
	}

	public void setIntegralReleaseDeduct(Integer integralReleaseDeduct) {
		this.integralReleaseDeduct = integralReleaseDeduct;
	}

	public Integer getIntegralAcceptDeduct() {
		return integralAcceptDeduct;
	}

	public void setIntegralAcceptDeduct(Integer integralAcceptDeduct) {
		this.integralAcceptDeduct = integralAcceptDeduct;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public Integer getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Integer coefficient) {
		this.coefficient = coefficient;
	}

	public Integer getCoinRegister() {
		return coinRegister;
	}

	public void setCoinRegister(Integer coinRegister) {
		this.coinRegister = coinRegister;
	}

	public Integer getCoinPerson() {
		return coinPerson;
	}

	public void setCoinPerson(Integer coinPerson) {
		this.coinPerson = coinPerson;
	}

	public Integer getCoinCard() {
		return coinCard;
	}

	public void setCoinCard(Integer coinCard) {
		this.coinCard = coinCard;
	}

	public Integer getCoinUs() {
		return coinUs;
	}

	public void setCoinUs(Integer coinUs) {
		this.coinUs = coinUs;
	}

	public Integer getCoinRec() {
		return coinRec;
	}

	public void setCoinRec(Integer coinRec) {
		this.coinRec = coinRec;
	}
}
