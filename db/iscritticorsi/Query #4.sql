SELECT s.CDS, COUNT(*) AS n
FROM studente s, iscrizione i
WHERE s.matricola = i.matricola AND i.codins = '01KSUPG' AND s.CDS<> ""
GROUP BY s.CDS;