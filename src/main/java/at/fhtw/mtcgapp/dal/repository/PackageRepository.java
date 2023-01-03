package at.fhtw.mtcgapp.dal.repository;

import at.fhtw.mtcgapp.dal.UnitOfWork;

public class PackageRepository {
    private UnitOfWork unitOfWork;
    public PackageRepository(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWork getUnitOfWork ()
    {
        return this.unitOfWork;
    }
}
