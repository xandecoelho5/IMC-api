package br.edu.utfpr.imc.dto;

import br.edu.utfpr.imc.model.Classification;

public record ImcResult(Double imc, Classification classification) {
}