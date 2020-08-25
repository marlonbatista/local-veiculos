export default interface Aluguel {
    cod_aluguel: number;
    cod_cliente: number;
    cod_veiculo: number;
    data_locacao: Date;
    data_devolucao: Date;
    valor_total: number;
    status: boolean;
}