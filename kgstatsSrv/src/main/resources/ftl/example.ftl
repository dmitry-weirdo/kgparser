<#-- a template just to test the freemarker -->
<#assign d = testDouble>
<#--double format: ${d?string[",00;; decimalSeparator=\",\""]}-->
double format: ${d?string(",##0.00;; decimalSeparator=\",\"")}

Test integer: ${testInteger?string[",000;; groupingSeparator=\" \""]}

<#assign x = testInteger>
X first assign: ${x}

<#assign x = 666>
X second assign: ${x}

<#-- import the macro from the other file -->
<#-- see https://stackoverflow.com/a/46788690/8534088 -->
<#import "./player-td.ftl" as ptd>
<@ptd.playerTd player=testPlayer/>

<#list testMap as key, value>
    ==========================================
    <#if key?is_first>
        I am first!
    <#else>
        I am NOT first!
    </#if>

    Key: ${key}
    Value: ${value}
    ==========================================
</#list>


<#if testMap['key 3']?? >
    <#assign key3Value = testMap['key 3']>
    key 3 is present. Value: ${key3Value}
<#else>
    key 3 is NOT present
</#if>

<#if testMap['key 4']?? >
    <#assign key4value = testMap['key 4']>
    key 4 is present. Value: ${key4value}
<#else>
    key 4 is NOT present
</#if>

String with non-breaking space: ${testStringWithNbsp}
