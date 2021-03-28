WITH T1(ITEMID, PARENTID, LVL, ROOT_ID, PATH, LABEL, FULL_PATH, PARENT_TYPE) AS (
    -- ANCHOR MEMBER.
    SELECT ITEMID,
           PARENTID,
           1                  AS LVL,
           ITEMID             AS ROOT_ID,
           TO_CHAR(ITEMID)    AS PATH,
           LABEL AS LABEL,
           LABEL AS FULL_PATH,
           '1'                AS PARENT_TYPE
    FROM LINKS2
    WHERE PARENTID IS NULL
      AND ITEMID = 'UG187'
    UNION ALL
    -- RECURSIVE MEMBER.
    SELECT T2.ITEMID,
           T2.PARENTID,
           LVL + 1,
           T1.ROOT_ID,
           T1.PATH || '~' || T2.ITEMID                                                            AS PATH,
           (SELECT LABEL FROM ITEMS2 WHERE ITEMS2.ITEMID = T2.ITEMID)                               AS LABEL,
           T1.FULL_PATH || '~' || (SELECT LABEL FROM ITEMS2 WHERE ITEMS2.ITEMID = T2.ITEMID)        AS FULL_PATH,
           T1.PARENT_TYPE || '~' || (SELECT ITEMS2.TYPE FROM ITEMS2 WHERE ITEMS2.ITEMID = T2.ITEMID) AS PARENT_TYPE
    FROM LINKS2 T2,
         T1
    WHERE T2.PARENTID = T1.ITEMID
)
         SEARCH DEPTH FIRST BY PARENTID SET ORDER1
SELECT ITEMID,
       PARENTID,
       RPAD('.', (LVL - 1) * 2, '.') || ITEMID AS TREE,
       LVL,
       ROOT_ID,
       PATH,
       LABEL,
       FULL_PATH,
       PARENT_TYPE
FROM T1
ORDER BY ORDER1