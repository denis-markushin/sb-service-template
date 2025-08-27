package @packageName@.integration.input

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import com.jayway.jsonpath.TypeRef
import com.netflix.graphql.dgs.DgsQueryExecutor
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import @group@.DgsClient
import @group@.types.@pascalCaseName@
import @group@.types.@pascalCaseName@Sort
import @packageName@.integration.AbstractIntegrationTest
import @packageName@.testdata.Test@pascalCaseName@Record

class @pascalCaseName@QueriesTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var queryExecutor: DgsQueryExecutor

    @Test
    fun `should fetch first 5 records sorted by CREATED_AT desc`() {
        // given
        List(10) { Test@pascalCaseName@Record() }.store()

        val query =
            DgsClient.buildQuery {
                @camelCaseNamePlural@ {
                    all(first = 5, sort = @pascalCaseName@Sort.CREATED_AT_DESC) {
                        pageInfo {
                            endCursor
                            hasNextPage
                            hasPreviousPage
                            startCursor
                        }
                        edges {
                            cursor
                            node {
                                id
                            }
                        }
                    }
                }
            }

        // when
        val result = queryExecutor.executeAndExtractJsonPathAsObject(
                query,
                "data.@camelCaseNamePlural@.all.edges[*].node",
                object : TypeRef<List<@pascalCaseName@>>() {},
            )

        // then
        assertThat(result).all {
            hasSize(5)
        }
    }
}
