function ExportToExcel(type, fn, dl) {
    const btn = document.getElementById('point-table');
    const wb = XLSX.utils.table_to_book(btn, { sheet: "sheet1" });
    return dl ?
        XLSX.write(wb, { bookType: type, bookSST: true, type: 'base64' }):
        XLSX.writeFile(wb, fn || ('TablaDePuntosPodrida.' + (type || 'xlsx')));
}