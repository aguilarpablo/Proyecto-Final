package es.eoi.mundobancario.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "amortizaciones")
public class Amortizacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column
	private Double importe;
	
	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean pagado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestamo", referencedColumnName = "id")
	private Prestamo prestamo;

}
