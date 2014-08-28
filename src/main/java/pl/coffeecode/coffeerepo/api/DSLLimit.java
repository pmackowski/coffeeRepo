package pl.coffeecode.coffeerepo.api;

public interface DSLLimit extends Excutable {

    /**
     * @param numberOfRows maximum number of rows returned
     * @param page         starts from 1 and determines offset, where offset = numberOfRows * (page-1)
     * @return
     */
    Excutable limit(int numberOfRows, int page);

}
