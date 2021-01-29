<#-- expects Vocabulary as vocabulary -->
<#macro vocabularyLink vocabulary>
    <#if vocabulary.link??>
        <a href="${vocabulary.link}" target="_blank" rel="noopener noreferrer">
            ${vocabulary.name}
        </a>
    <#else>
        ${vocabulary.name}
    </#if>
</#macro>
