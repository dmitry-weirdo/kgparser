<#-- expects PlayerVocabularyDto as player, or something with same used properties -->
<#macro playerTd player>
                    <td class="${player.rank}">
                        <span class="login">${player.login}</span>
                        <a href="${player.vocabularyStatsLink}" target="_blank" rel="noopener noreferrer">
                            <img src="${links.infoPng}" alt="Профиль" title="Профиль" width="10" height="10"/>
                        </a>
                    </td>
</#macro>
