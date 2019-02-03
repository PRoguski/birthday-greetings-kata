package it.xpug.kata.birthday_greetings.domain

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import spock.lang.Specification

class EmployeeTest extends Specification {

    def toLocalDate = { String yyyyMMdd -> LocalDate.parse(yyyyMMdd, DateTimeFormat.forPattern("yyyy/MM/dd")) }

    def "birthday"() {
        expect:
        new Employee("foo", "bar", toLocalDate("1990/01/31"), "a@b.c").isBirthday(toLocalDate(day)) == isBirthDay
        where:
        day          || isBirthDay
        "1990/01/31" || true
        "1990/01/30" || false
    }

    def "equals test"() {
        when:
        Employee base = new Employee("First", "Last", toLocalDate("1999/09/01"), "first@last.com")
        Employee same = new Employee("First", "Last", toLocalDate("1999/09/01"), "first@last.com")
        Employee different = new Employee("First", "Last", toLocalDate("1999/09/01"), "boom@boom.com")
        then:
        base == same
        base != different
        same != different
    }
}
